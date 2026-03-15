package com.rashid.backend.service.impl;

import com.rashid.backend.dto.ProjectDTO;
import com.rashid.backend.model.Project;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.ProjectRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TeamRepository;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, TeamRepository teamRepository,
                              UserRepository userRepository, TeamMemberRepository teamMemberRepository) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
                
        // Verification that user is in the team should be done here in real app
        
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setTeam(team);
        
        Project savedProject = projectRepository.save(project);
        dto.setId(savedProject.getId());
        return dto;
    }

    @Override
    public List<ProjectDTO> getProjectsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
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
