package com.rashid.backend.controller;

import com.rashid.backend.dto.dashboard.DashboardStatsDTO;
import com.rashid.backend.dto.dashboard.OverviewDTO;
import com.rashid.backend.dto.dashboard.TeamDashboardDTO;
import com.rashid.backend.service.interfaces.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats(Authentication authentication) {
        DashboardStatsDTO stats = dashboardService.getStatsForUser(authentication.getName());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/overview")
    public ResponseEntity<OverviewDTO> getOverview(Authentication authentication) {
        return ResponseEntity.ok(dashboardService.getOverviewForUser(authentication.getName()));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<TeamDashboardDTO> getTeamDashboard(@PathVariable Long teamId, Authentication authentication) {
        return ResponseEntity.ok(dashboardService.getTeamDashboard(teamId, authentication.getName()));
    }
}
