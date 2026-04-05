package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.report.CompletedTasksReportDTO;
import com.rashid.backend.dto.report.TeamHoursDTO;
import com.rashid.backend.dto.report.TrendsOverviewDTO;
import com.rashid.backend.dto.report.WeeklySummaryDTO;

public interface ReportService {
    WeeklySummaryDTO getWeeklySummary(String username);
    TeamHoursDTO getTeamHours(Long teamId, String username);
    CompletedTasksReportDTO getCompletedTasksReport(Long teamId, String username);
    TrendsOverviewDTO getTrendsOverview(Long teamId, String username);
}
