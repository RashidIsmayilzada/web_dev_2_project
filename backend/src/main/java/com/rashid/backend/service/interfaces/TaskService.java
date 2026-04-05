package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.task.TaskDTO;
import com.rashid.backend.dto.task.TaskFilterDTO;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO, String username);
    PagedResponseDTO<TaskDTO> getTasksForProject(Long projectId, int page, int size, String username);
    PagedResponseDTO<TaskDTO> getTasks(TaskFilterDTO filter, int page, int size, String username);
    TaskDTO updateTask(Long taskId, TaskDTO taskDTO, String username);
    void deleteTask(Long taskId, String username);
}
