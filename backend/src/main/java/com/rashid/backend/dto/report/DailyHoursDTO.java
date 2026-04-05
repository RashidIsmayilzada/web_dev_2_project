package com.rashid.backend.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyHoursDTO {
    private String day;
    private double hours;
}
