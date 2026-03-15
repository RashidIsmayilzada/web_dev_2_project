package com.rashid.backend.service.impl;

import com.rashid.backend.dto.DashboardStatsDTO;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.ProjectRepository;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TimeLogRepository timeLogRepository;

    public DashboardServiceImpl(UserRepository userRepository, TeamMemberRepository teamMemberRepository,
                                ProjectRepository projectRepository, TaskRepository taskRepository,
                                TimeLogRepository timeLogRepository) {
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.timeLogRepository = timeLogRepository;
    }

    @Override
    public DashboardStatsDTO getStatsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TeamMember> userTeams = teamMemberRepository.findByUserId(user.getId());
        List<Long> teamIds = userTeams.stream()
                .map(tm -> tm.getTeam().getId())
                .collect(Collectors.toList());

        long totalProjects = teamIds.isEmpty() ? 0 : projectRepository.countByTeamIdIn(teamIds);
        long activeTasks = taskRepository.countByAssigneeIdAndStatus(user.getId(), "IN_PROGRESS");

        List<TimeLog> logs = timeLogRepository.findByUserId(user.getId());
        long totalSeconds = logs.stream()
                .filter(l -> l.getStartTime() != null && l.getEndTime() != null)
                .mapToLong(l -> Duration.between(l.getStartTime(), l.getEndTime()).getSeconds())
                .sum();
        
        long totalHours = totalSeconds / 3600;

        DashboardStatsDTO stats = new DashboardStatsDTO();
        stats.setTotalProjects(totalProjects);
        stats.setActiveTasks(activeTasks);
        stats.setHoursTracked(totalHours);

        return stats;
    }
}
