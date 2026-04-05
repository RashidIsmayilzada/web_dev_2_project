package com.rashid.backend.controller;

import com.rashid.backend.dto.report.CompletedTasksReportDTO;
import com.rashid.backend.dto.report.TeamHoursDTO;
import com.rashid.backend.dto.report.TrendsOverviewDTO;
import com.rashid.backend.dto.report.WeeklySummaryDTO;
import com.rashid.backend.service.interfaces.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/weekly-summary")
    public ResponseEntity<WeeklySummaryDTO> getWeeklySummary(Authentication authentication) {
        return ResponseEntity.ok(reportService.getWeeklySummary(authentication.getName()));
    }

    @GetMapping("/team-hours")
    public ResponseEntity<TeamHoursDTO> getTeamHours(@RequestParam Long teamId, Authentication authentication) {
        return ResponseEntity.ok(reportService.getTeamHours(teamId, authentication.getName()));
    }

    @GetMapping("/completed-tasks")
    public ResponseEntity<CompletedTasksReportDTO> getCompletedTasks(@RequestParam Long teamId, Authentication authentication) {
        return ResponseEntity.ok(reportService.getCompletedTasksReport(teamId, authentication.getName()));
    }

    @GetMapping("/trends")
    public ResponseEntity<TrendsOverviewDTO> getTrends(@RequestParam Long teamId, Authentication authentication) {
        return ResponseEntity.ok(reportService.getTrendsOverview(teamId, authentication.getName()));
    }
}
