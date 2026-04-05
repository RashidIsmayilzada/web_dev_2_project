package com.rashid.backend.service;

import com.rashid.backend.dto.report.CompletedTaskItemDTO;
import com.rashid.backend.dto.report.CompletedTasksReportDTO;
import com.rashid.backend.dto.report.DailyHoursDTO;
import com.rashid.backend.dto.report.MemberHoursDTO;
import com.rashid.backend.dto.report.TeamHoursDTO;
import com.rashid.backend.dto.report.TrendPointDTO;
import com.rashid.backend.dto.report.TrendsOverviewDTO;
import com.rashid.backend.dto.report.WeeklySummaryDTO;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.service.interfaces.ReportService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final TimeLogRepository timeLogRepository;
    private final TaskRepository taskRepository;
    private final AuthorizationService authorizationService;

    public ReportServiceImpl(
            TimeLogRepository timeLogRepository,
            TaskRepository taskRepository,
            AuthorizationService authorizationService
    ) {
        this.timeLogRepository = timeLogRepository;
        this.taskRepository = taskRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public WeeklySummaryDTO getWeeklySummary(String username) {
        User user = authorizationService.getRequiredUser(username);
        LocalDate start = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(6);
        List<TimeLog> logs = timeLogRepository.findByUserIdAndStartTimeBetween(
                user.getId(),
                start.atStartOfDay(),
                end.atTime(23, 59, 59)
        );

        Map<String, Double> dailyHours = initializeDayMap(start);
        for (TimeLog log : logs) {
            if (log.getStartTime() != null && log.getEndTime() != null) {
                String day = log.getStartTime().toLocalDate().getDayOfWeek().name();
                dailyHours.computeIfPresent(day, (key, value) -> value + getHours(log));
            }
        }

        WeeklySummaryDTO dto = new WeeklySummaryDTO();
        dto.setTotalHours(roundHours(logs.stream().mapToDouble(this::getHours).sum()));
        dto.setEntriesCount(logs.size());
        dto.setDailyHours(dailyHours.entrySet().stream()
                .map(entry -> new DailyHoursDTO(entry.getKey(), roundHours(entry.getValue())))
                .toList());
        return dto;
    }

    @Override
    public TeamHoursDTO getTeamHours(Long teamId, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireTeamMembership(teamId, user.getId());
        Team team = authorizationService.getRequiredTeam(teamId);
        List<TimeLog> logs = timeLogRepository.findByTaskProjectTeamId(teamId);

        Map<User, Double> byUser = logs.stream()
                .filter(log -> log.getStartTime() != null && log.getEndTime() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                        TimeLog::getUser,
                        java.util.stream.Collectors.summingDouble(this::getHours)
                ));

        TeamHoursDTO dto = new TeamHoursDTO();
        dto.setTeamId(teamId);
        dto.setTeamName(team.getName());
        dto.setTotalHours(roundHours(logs.stream().mapToDouble(this::getHours).sum()));
        dto.setMemberHours(byUser.entrySet().stream()
                .map(entry -> new MemberHoursDTO(entry.getKey().getId(), entry.getKey().getUsername(), roundHours(entry.getValue())))
                .sorted(java.util.Comparator.comparing(MemberHoursDTO::getHours).reversed())
                .toList());
        return dto;
    }

    @Override
    public CompletedTasksReportDTO getCompletedTasksReport(Long teamId, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireTeamMembership(teamId, user.getId());

        List<CompletedTaskItemDTO> tasks = taskRepository.findAll().stream()
                .filter(task -> task.getProject().getTeam().getId().equals(teamId))
                .filter(task -> "DONE".equals(task.getStatus()))
                .map(task -> new CompletedTaskItemDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getProject().getName(),
                        task.getAssignee() != null ? task.getAssignee().getUsername() : "Unassigned"
                ))
                .toList();

        CompletedTasksReportDTO dto = new CompletedTasksReportDTO();
        dto.setTeamId(teamId);
        dto.setTotalCompletedTasks(tasks.size());
        dto.setTasks(tasks);
        return dto;
    }

    @Override
    public TrendsOverviewDTO getTrendsOverview(Long teamId, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireTeamMembership(teamId, user.getId());
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);
        List<TimeLog> logs = timeLogRepository.findByTaskProjectTeamIdAndStartTimeBetween(
                teamId,
                startDate.atStartOfDay(),
                today.atTime(23, 59, 59)
        );
        List<Task> teamTasks = taskRepository.findAll().stream()
                .filter(task -> task.getProject().getTeam().getId().equals(teamId))
                .toList();

        List<TrendPointDTO> hoursTrend = startDate.datesUntil(today.plusDays(1))
                .map(date -> new TrendPointDTO(date.toString(), roundHours(logs.stream()
                        .filter(log -> log.getStartTime() != null && log.getStartTime().toLocalDate().equals(date))
                        .mapToDouble(this::getHours)
                        .sum())))
                .toList();

        List<TrendPointDTO> completedTasksTrend = startDate.datesUntil(today.plusDays(1))
                .map(date -> new TrendPointDTO(date.toString(), teamTasks.stream()
                        .filter(task -> "DONE".equals(task.getStatus()))
                        .filter(task -> task.getId() != null)
                        .count()))
                .toList();

        TrendsOverviewDTO dto = new TrendsOverviewDTO();
        dto.setTeamId(teamId);
        dto.setHoursTrend(hoursTrend);
        dto.setCompletedTasksTrend(completedTasksTrend);
        return dto;
    }

    private Map<String, Double> initializeDayMap(LocalDate start) {
        Map<String, Double> result = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {
            result.put(start.plusDays(i).getDayOfWeek().name(), 0.0);
        }
        return result;
    }

    private double getHours(TimeLog log) {
        if (log.getStartTime() == null || log.getEndTime() == null) {
            return 0.0;
        }
        return Duration.between(log.getStartTime(), log.getEndTime()).toMinutes() / 60.0;
    }

    private double roundHours(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
