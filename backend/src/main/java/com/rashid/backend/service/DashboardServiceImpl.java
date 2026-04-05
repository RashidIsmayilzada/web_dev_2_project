package com.rashid.backend.service;

import com.rashid.backend.dto.dashboard.DashboardStatsDTO;
import com.rashid.backend.dto.dashboard.OverviewDTO;
import com.rashid.backend.dto.dashboard.TeamDashboardDTO;
import com.rashid.backend.dto.report.MemberHoursDTO;
import com.rashid.backend.dto.task.TaskDTO;
import com.rashid.backend.dto.timelog.TimeLogDTO;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.User;
import com.rashid.backend.model.enums.InviteStatus;
import com.rashid.backend.repository.ProjectRepository;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TeamInviteRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.service.interfaces.DashboardService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamInviteRepository teamInviteRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TimeLogRepository timeLogRepository;
    private final AuthorizationService authorizationService;
    private final TimeLogServiceImpl timeLogService;

    public DashboardServiceImpl(
            TeamMemberRepository teamMemberRepository,
            TeamInviteRepository teamInviteRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            TimeLogRepository timeLogRepository,
            AuthorizationService authorizationService,
            TimeLogServiceImpl timeLogService
    ) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamInviteRepository = teamInviteRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.timeLogRepository = timeLogRepository;
        this.authorizationService = authorizationService;
        this.timeLogService = timeLogService;
    }

    @Override
    public DashboardStatsDTO getStatsForUser(String username) {
        User user = authorizationService.getRequiredUser(username);
        List<TeamMember> userTeams = teamMemberRepository.findByUserId(user.getId());
        List<Long> teamIds = userTeams.stream().map(tm -> tm.getTeam().getId()).toList();

        DashboardStatsDTO stats = new DashboardStatsDTO();
        stats.setTotalTeams(teamIds.size());
        stats.setTotalProjects(teamIds.isEmpty() ? 0 : projectRepository.countByTeamIdIn(teamIds));
        stats.setActiveTasks(taskRepository.countByAssigneeIdAndStatus(user.getId(), "IN_PROGRESS"));
        stats.setHoursTracked(calculateHours(timeLogRepository.findByUserId(user.getId())));
        stats.setPendingInvites(teamInviteRepository.findByInvitedUserIdAndStatusOrderByCreatedAtDesc(user.getId(), InviteStatus.PENDING).size());
        return stats;
    }

    @Override
    public OverviewDTO getOverviewForUser(String username) {
        User user = authorizationService.getRequiredUser(username);
        List<TaskDTO> recentTasks = taskRepository.findAll().stream()
                .filter(task -> task.getAssignee() != null && task.getAssignee().getId().equals(user.getId()))
                .sorted(Comparator.comparing(Task::getId).reversed())
                .limit(5)
                .map(this::mapTask)
                .toList();

        List<TimeLogDTO> recentTimeLogs = timeLogRepository.findByUserId(user.getId()).stream()
                .sorted(Comparator.comparing(TimeLog::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .limit(5)
                .map(timeLogService::mapTimeLog)
                .toList();

        OverviewDTO overview = new OverviewDTO();
        overview.setStats(getStatsForUser(username));
        overview.setRecentTasks(recentTasks);
        overview.setRecentTimeLogs(recentTimeLogs);
        return overview;
    }

    @Override
    public TeamDashboardDTO getTeamDashboard(Long teamId, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireTeamMembership(teamId, user.getId());
        Team team = authorizationService.getRequiredTeam(teamId);
        List<TimeLog> teamLogs = timeLogRepository.findByTaskProjectTeamId(teamId);

        Map<User, Double> memberHours = teamLogs.stream()
                .filter(log -> log.getStartTime() != null && log.getEndTime() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        TimeLog::getUser,
                        java.util.stream.Collectors.summingDouble(log -> Duration.between(log.getStartTime(), log.getEndTime()).toMinutes() / 60.0)
                ));

        TeamDashboardDTO dto = new TeamDashboardDTO();
        dto.setTeamId(teamId);
        dto.setTeamName(team.getName());
        dto.setTotalProjects(projectRepository.findByTeamId(teamId).size());
        dto.setTotalTasks(taskRepository.findAll().stream().filter(task -> task.getProject().getTeam().getId().equals(teamId)).count());
        dto.setCompletedTasks(taskRepository.countByProjectTeamIdAndStatus(teamId, "DONE"));
        dto.setTotalHours(calculateHours(teamLogs));
        dto.setMemberHours(memberHours.entrySet().stream()
                .map(entry -> new MemberHoursDTO(entry.getKey().getId(), entry.getKey().getUsername(), roundHours(entry.getValue())))
                .sorted(Comparator.comparing(MemberHoursDTO::getHours).reversed())
                .toList());
        return dto;
    }

    private long calculateHours(List<TimeLog> logs) {
        long totalSeconds = logs.stream()
                .filter(l -> l.getStartTime() != null && l.getEndTime() != null)
                .mapToLong(l -> Duration.between(l.getStartTime(), l.getEndTime()).getSeconds())
                .sum();
        return totalSeconds / 3600;
    }

    private double roundHours(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private TaskDTO mapTask(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus());
        dto.setProjectId(task.getProject().getId());
        dto.setProjectName(task.getProject().getName());
        dto.setTeamId(task.getProject().getTeam().getId());
        if (task.getAssignee() != null) {
            dto.setAssigneeId(task.getAssignee().getId());
            dto.setAssigneeName(task.getAssignee().getUsername());
            dto.setAssigneeUsername(task.getAssignee().getUsername());
        }
        return dto;
    }
}
