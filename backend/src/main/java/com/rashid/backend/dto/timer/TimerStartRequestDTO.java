package com.rashid.backend.dto.timer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TimerStartRequestDTO {
    @NotNull(message = "Task is required")
    private Long taskId;
    private String description;
}
