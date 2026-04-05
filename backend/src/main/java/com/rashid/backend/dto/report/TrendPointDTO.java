package com.rashid.backend.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrendPointDTO {
    private String label;
    private double value;
}
