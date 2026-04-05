package com.rashid.backend.dto.team;

import com.rashid.backend.model.enums.TeamRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeamRoleUpdateDTO {
    @NotNull(message = "Role is required")
    private TeamRole role;
}
