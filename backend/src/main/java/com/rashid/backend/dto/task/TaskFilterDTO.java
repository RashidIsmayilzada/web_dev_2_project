package com.rashid.backend.dto.task;

import lombok.Data;

@Data
public class TaskFilterDTO {
    private Long projectId;
    private Long teamId;
    private String status;
    private String assignee;
    private String search;
}
