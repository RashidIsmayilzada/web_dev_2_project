package com.rashid.backend.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompletedTaskItemDTO {
    private Long taskId;
    private String title;
    private String projectName;
    private String assigneeUsername;
}
