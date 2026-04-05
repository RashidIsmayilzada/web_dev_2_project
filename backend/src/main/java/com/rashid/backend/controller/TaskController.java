package com.rashid.backend.controller;

import com.rashid.backend.dto.task.TaskDTO;
import com.rashid.backend.dto.task.TaskFilterDTO;
import com.rashid.backend.service.interfaces.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, Authentication authentication) {
        TaskDTO created = taskService.createTask(taskDTO, authentication.getName());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String search,
            Authentication authentication
    ) {
        TaskFilterDTO filter = new TaskFilterDTO();
        filter.setProjectId(projectId);
        filter.setTeamId(teamId);
        filter.setStatus(status);
        filter.setAssignee(assignee);
        filter.setSearch(search);
        return ResponseEntity.ok(taskService.getTasks(filter, authentication.getName()));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskDTO taskDTO,
            Authentication authentication
    ) {
        TaskDTO updated = taskService.updateTask(taskId, taskDTO, authentication.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, Authentication authentication) {
        taskService.deleteTask(taskId, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDTO>> getTasksForProject(@PathVariable Long projectId, Authentication authentication) {
        List<TaskDTO> tasks = taskService.getTasksForProject(projectId, authentication.getName());
        return ResponseEntity.ok(tasks);
    }
}
