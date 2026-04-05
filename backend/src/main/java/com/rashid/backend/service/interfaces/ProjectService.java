package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.project.ProjectDTO;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO, String username);
    PagedResponseDTO<ProjectDTO> getProjectsForUser(int page, int size, String username);
}
