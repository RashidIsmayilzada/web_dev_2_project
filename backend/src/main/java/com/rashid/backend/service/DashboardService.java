package com.rashid.backend.service;

import com.rashid.backend.dto.DashboardStatsDTO;

public interface DashboardService {
    DashboardStatsDTO getStatsForUser(String username);
}
