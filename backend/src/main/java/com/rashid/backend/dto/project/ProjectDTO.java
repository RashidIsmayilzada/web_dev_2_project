package com.rashid.backend.dto.project;

import lombok.Data;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private Long teamId;
}
