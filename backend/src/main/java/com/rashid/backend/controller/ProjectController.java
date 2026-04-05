package com.rashid.backend.controller;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.project.ProjectDTO;
import com.rashid.backend.service.interfaces.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO, Authentication authentication) {
        ProjectDTO created = projectService.createProject(projectDTO, authentication.getName());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<ProjectDTO>> getProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(projectService.getProjectsForUser(page, size, authentication.getName()));
    }
}
