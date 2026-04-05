package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.task.TaskDTO;
import com.rashid.backend.dto.task.TaskFilterDTO;
import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO, String username);
    List<TaskDTO> getTasksForProject(Long projectId, String username);
    List<TaskDTO> getTasks(TaskFilterDTO filter, String username);
    TaskDTO updateTask(Long taskId, TaskDTO taskDTO, String username);
    void deleteTask(Long taskId, String username);
}
