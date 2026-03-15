package com.rashid.backend.controller;

import com.rashid.backend.dto.TaskDTO;
import com.rashid.backend.service.TaskService;
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

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDTO>> getTasksForProject(@PathVariable Long projectId, Authentication authentication) {
        List<TaskDTO> tasks = taskService.getTasksForProject(projectId, authentication.getName());
        return ResponseEntity.ok(tasks);
    }
}
