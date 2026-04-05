package com.rashid.backend.service;

import com.rashid.backend.dto.project.ProjectDTO;
import com.rashid.backend.model.Project;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.ProjectRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.service.interfaces.ProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final AuthorizationService authorizationService;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            TeamMemberRepository teamMemberRepository,
            AuthorizationService authorizationService
    ) {
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO dto, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireManagerOrOwner(dto.getTeamId(), user.getId());

        Project project = new Project();
        project.setName(dto.getName().trim());
        project.setDescription(dto.getDescription());
        project.setTeam(authorizationService.getRequiredTeam(dto.getTeamId()));

        Project savedProject = projectRepository.save(project);
        dto.setId(savedProject.getId());
        return dto;
    }

    @Override
    public List<ProjectDTO> getProjectsForUser(String username) {
        User user = authorizationService.getRequiredUser(username);
        List<TeamMember> userTeams = teamMemberRepository.findByUserId(user.getId());
        List<ProjectDTO> projectDTOs = new ArrayList<>();

        for (TeamMember tm : userTeams) {
            List<Project> projects = projectRepository.findByTeamId(tm.getTeam().getId());
            for (Project p : projects) {
                ProjectDTO dto = new ProjectDTO();
                dto.setId(p.getId());
                dto.setName(p.getName());
                dto.setDescription(p.getDescription());
                dto.setTeamId(tm.getTeam().getId());
                projectDTOs.add(dto);
            }
        }
        return projectDTOs;
    }
}
