package com.rashid.backend.service;

import com.rashid.backend.dto.TaskDTO;
import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO, String username);
    List<TaskDTO> getTasksForProject(Long projectId, String username);
}
