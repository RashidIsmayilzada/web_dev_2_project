package com.rashid.backend.service.impl;

import com.rashid.backend.dto.TaskDTO;
import com.rashid.backend.model.Project;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.ProjectRepository;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO createTask(TaskDTO dto, String username) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
                
        User assignee = null;
        if (dto.getAssigneeId() != null) {
            assignee = userRepository.findById(dto.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : "TODO");
        task.setProject(project);
        task.setAssignee(assignee);
        
        Task savedTask = taskRepository.save(task);
        
        dto.setId(savedTask.getId());
        dto.setStatus(savedTask.getStatus());
        if (assignee != null) {
            dto.setAssigneeName(assignee.getUsername());
        }
        return dto;
    }

    @Override
    public List<TaskDTO> getTasksForProject(Long projectId, String username) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream().map(task -> {
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setStatus(task.getStatus());
            dto.setProjectId(task.getProject().getId());
            if (task.getAssignee() != null) {
                dto.setAssigneeId(task.getAssignee().getId());
                dto.setAssigneeName(task.getAssignee().getUsername());
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
