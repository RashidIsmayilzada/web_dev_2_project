package com.rashid.backend.service;

import com.rashid.backend.exception.AccessDeniedException;
import com.rashid.backend.exception.ResourceNotFoundException;
import com.rashid.backend.model.Project;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.User;
import com.rashid.backend.model.enums.TeamRole;
import com.rashid.backend.repository.ProjectRepository;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TeamRepository;
import com.rashid.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public AuthorizationService(
            UserRepository userRepository,
            TeamRepository teamRepository,
            TeamMemberRepository teamMemberRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository
    ) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public User getRequiredUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Team getRequiredTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }

    public Project getRequiredProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    public Task getRequiredTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public TeamMember requireTeamMembership(Long teamId, Long userId) {
        return teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new AccessDeniedException("You do not have access to this team"));
    }

    public TeamMember requireManagerOrOwner(Long teamId, Long userId) {
        TeamMember membership = requireTeamMembership(teamId, userId);
        if (membership.getRole() == TeamRole.MEMBER) {
            throw new AccessDeniedException("You do not have permission to manage this team");
        }
        return membership;
    }

    public TeamMember requireOwner(Long teamId, Long userId) {
        TeamMember membership = requireTeamMembership(teamId, userId);
        if (membership.getRole() != TeamRole.OWNER) {
            throw new AccessDeniedException("Only the team owner can perform this action");
        }
        return membership;
    }

    public void requireProjectAccess(Project project, Long userId) {
        requireTeamMembership(project.getTeam().getId(), userId);
    }

    public void requireTaskAccess(Task task, Long userId) {
        requireProjectAccess(task.getProject(), userId);
    }
}
