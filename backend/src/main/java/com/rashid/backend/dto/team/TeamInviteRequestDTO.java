package com.rashid.backend.dto.team;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamInviteRequestDTO {
    @NotBlank(message = "Username is required")
    private String username;
}
