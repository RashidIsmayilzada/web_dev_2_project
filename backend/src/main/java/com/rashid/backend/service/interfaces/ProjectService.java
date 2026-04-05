package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.project.ProjectDTO;
import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO, String username);
    List<ProjectDTO> getProjectsForUser(String username);
}
