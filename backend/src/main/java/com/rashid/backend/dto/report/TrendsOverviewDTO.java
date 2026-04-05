package com.rashid.backend.dto.report;

import lombok.Data;

import java.util.List;

@Data
public class TrendsOverviewDTO {
    private Long teamId;
    private List<TrendPointDTO> hoursTrend;
    private List<TrendPointDTO> completedTasksTrend;
}
