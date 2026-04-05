package com.rashid.backend.dto.team;

import com.rashid.backend.model.enums.TeamRole;
import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private String description;
    private TeamRole currentUserRole;
    private int memberCount;
    private int pendingInviteCount;
}
