package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.dashboard.DashboardStatsDTO;
import com.rashid.backend.dto.dashboard.OverviewDTO;
import com.rashid.backend.dto.dashboard.TeamDashboardDTO;

public interface DashboardService {
    DashboardStatsDTO getStatsForUser(String username);
    OverviewDTO getOverviewForUser(String username);
    TeamDashboardDTO getTeamDashboard(Long teamId, String username);
}
