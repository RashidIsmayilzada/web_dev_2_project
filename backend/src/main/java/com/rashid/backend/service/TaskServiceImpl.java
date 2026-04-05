package com.rashid.backend.service;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.task.TaskDTO;
import com.rashid.backend.dto.task.TaskFilterDTO;
import com.rashid.backend.exception.ResourceNotFoundException;
import com.rashid.backend.model.Project;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.service.interfaces.TaskService;
import com.rashid.backend.util.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final AuthorizationService authorizationService;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            UserRepository userRepository,
            TeamMemberRepository teamMemberRepository,
            AuthorizationService authorizationService
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public TaskDTO createTask(TaskDTO dto, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        Project project = authorizationService.getRequiredProject(dto.getProjectId());
        authorizationService.requireManagerOrOwner(project.getTeam().getId(), currentUser.getId());
        User assignee = resolveAssignee(dto, project);

        Task task = new Task();
        task.setTitle(dto.getTitle().trim());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : "TODO");
        task.setProject(project);
        task.setAssignee(assignee);

        return mapTask(taskRepository.save(task));
    }

    @Override
    public PagedResponseDTO<TaskDTO> getTasksForProject(Long projectId, int page, int size, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        Project project = authorizationService.getRequiredProject(projectId);
        authorizationService.requireProjectAccess(project, currentUser.getId());

        List<TaskDTO> tasks = taskRepository.findByProjectId(projectId).stream()
                .map(this::mapTask)
                .toList();

        return PagedResponseDTO.from(PaginationUtils.paginate(tasks, page, size));
    }

    @Override
    public PagedResponseDTO<TaskDTO> getTasks(TaskFilterDTO filter, int page, int size, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        List<Long> teamIds = teamMemberRepository.findByUserId(currentUser.getId()).stream()
                .map(member -> member.getTeam().getId())
                .toList();

        if (teamIds.isEmpty()) {
            return PagedResponseDTO.from(Page.empty(PageRequest.of(Math.max(page, 0), Math.max(size, 1))));
        }

        if (filter.getTeamId() != null) {
            authorizationService.requireTeamMembership(filter.getTeamId(), currentUser.getId());
        }

        Specification<Task> specification = Specification.where((root, query, cb) -> root.get("project").get("team").get("id").in(teamIds));

        if (filter.getProjectId() != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("project").get("id"), filter.getProjectId()));
        }
        if (filter.getTeamId() != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("project").get("team").get("id"), filter.getTeamId()));
        }
        if (filter.getStatus() != null && !filter.getStatus().isBlank()) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("status"), filter.getStatus().trim()));
        }
        if (filter.getAssignee() != null && !filter.getAssignee().isBlank()) {
            String assignee = "%" + filter.getAssignee().trim().toLowerCase() + "%";
            specification = specification.and((root, query, cb) -> cb.like(cb.lower(root.get("assignee").get("username")), assignee));
        }
        if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
            String search = "%" + filter.getSearch().trim().toLowerCase() + "%";
            specification = specification.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), search));
        }

        Page<Task> taskPage = taskRepository.findAll(
                specification,
                PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by(Sort.Direction.DESC, "id"))
        );

        return PagedResponseDTO.from(taskPage.map(this::mapTask));
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO dto, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        Task task = authorizationService.getRequiredTask(taskId);
        authorizationService.requireManagerOrOwner(task.getProject().getTeam().getId(), currentUser.getId());

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            task.setTitle(dto.getTitle().trim());
        }
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            task.setStatus(dto.getStatus().trim());
        }
        if (dto.getAssigneeId() != null || dto.getAssigneeUsername() != null) {
            task.setAssignee(resolveAssignee(dto, task.getProject()));
        }
        if (dto.getAssigneeId() == null && dto.getAssigneeUsername() != null && dto.getAssigneeUsername().isBlank()) {
            task.setAssignee(null);
        }

        return mapTask(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long taskId, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        Task task = authorizationService.getRequiredTask(taskId);
        authorizationService.requireManagerOrOwner(task.getProject().getTeam().getId(), currentUser.getId());
        taskRepository.delete(task);
    }

    private User resolveAssignee(TaskDTO dto, Project project) {
        if (dto.getAssigneeUsername() != null && !dto.getAssigneeUsername().isBlank()) {
            User assignee = userRepository.findByUsername(dto.getAssigneeUsername().trim())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
            ensureAssigneeBelongsToProjectTeam(assignee, project);
            return assignee;
        }

        if (dto.getAssigneeId() != null) {
            User assignee = userRepository.findById(dto.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
            ensureAssigneeBelongsToProjectTeam(assignee, project);
            return assignee;
        }

        return null;
    }

    private void ensureAssigneeBelongsToProjectTeam(User assignee, Project project) {
        List<TeamMember> members = teamMemberRepository.findByTeamId(project.getTeam().getId());
        boolean isTeamMember = members.stream().anyMatch(member -> member.getUser().getId().equals(assignee.getId()));

        if (!isTeamMember) {
            throw new ResourceNotFoundException("Assignee must be a member of the project team");
        }
    }

    private TaskDTO mapTask(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus());
        dto.setProjectId(task.getProject().getId());
        dto.setProjectName(task.getProject().getName());
        dto.setTeamId(task.getProject().getTeam().getId());
        if (task.getAssignee() != null) {
            dto.setAssigneeId(task.getAssignee().getId());
            dto.setAssigneeName(task.getAssignee().getUsername());
            dto.setAssigneeUsername(task.getAssignee().getUsername());
        }
        return dto;
    }
}
