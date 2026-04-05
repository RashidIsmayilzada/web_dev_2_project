package com.rashid.backend.dto.team;

import com.rashid.backend.model.enums.TeamRole;
import lombok.Data;

@Data
public class TeamMemberDTO {
    private Long userId;
    private String username;
    private String email;
    private TeamRole role;
}
