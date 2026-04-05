package com.rashid.backend.service.impl;

import com.rashid.backend.dto.TimeLogDTO;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.service.TimeLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeLogServiceImpl implements TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TimeLogServiceImpl(TimeLogRepository timeLogRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.timeLogRepository = timeLogRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TimeLogDTO logTime(TimeLogDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TimeLog log = new TimeLog();
        log.setTask(task);
        log.setUser(user);
        log.setStartTime(dto.getStartTime());
        log.setEndTime(dto.getEndTime());
        log.setDescription(dto.getDescription());
        
        TimeLog savedLog = timeLogRepository.save(log);
        
        dto.setId(savedLog.getId());
        dto.setTaskTitle(task.getTitle());
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    @Override
    public List<TimeLogDTO> getUserTimeLogs(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        List<TimeLog> logs = timeLogRepository.findByUserId(user.getId());
        return logs.stream().map(log -> {
            TimeLogDTO dto = new TimeLogDTO();
            dto.setId(log.getId());
            dto.setTaskId(log.getTask().getId());
            dto.setTaskTitle(log.getTask().getTitle());
            dto.setUserId(log.getUser().getId());
            dto.setUsername(log.getUser().getUsername());
            dto.setStartTime(log.getStartTime());
            dto.setEndTime(log.getEndTime());
            dto.setDescription(log.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }
}
