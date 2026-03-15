package com.rashid.backend.service;

import com.rashid.backend.dto.ProjectDTO;
import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO, String username);
    List<ProjectDTO> getProjectsForUser(String username);
}
