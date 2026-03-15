package com.rashid.backend.dto;

import com.rashid.backend.model.TeamRole;
import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private String description;
    private TeamRole currentUserRole;
}
