package com.rashid.backend.dto.report;

import lombok.Data;

import java.util.List;

@Data
public class WeeklySummaryDTO {
    private double totalHours;
    private int entriesCount;
    private List<DailyHoursDTO> dailyHours;
}
