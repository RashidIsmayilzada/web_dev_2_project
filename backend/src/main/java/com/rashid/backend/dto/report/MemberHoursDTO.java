package com.rashid.backend.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberHoursDTO {
    private Long userId;
    private String username;
    private double hours;
}
