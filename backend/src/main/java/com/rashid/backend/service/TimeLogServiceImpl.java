package com.rashid.backend.service;

import com.rashid.backend.dto.timelog.TimeLogDTO;
import com.rashid.backend.exception.BadRequestException;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.service.interfaces.TimeLogService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TimeLogServiceImpl implements TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final AuthorizationService authorizationService;

    public TimeLogServiceImpl(TimeLogRepository timeLogRepository, AuthorizationService authorizationService) {
        this.timeLogRepository = timeLogRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public TimeLogDTO logTime(TimeLogDTO dto, String username) {
        User user = authorizationService.getRequiredUser(username);
        Task task = authorizationService.getRequiredTask(dto.getTaskId());
        authorizationService.requireTaskAccess(task, user.getId());

        if (dto.getStartTime() == null || dto.getEndTime() == null || !dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new BadRequestException("End time must be after start time");
        }

        TimeLog log = new TimeLog();
        log.setTask(task);
        log.setUser(user);
        log.setStartTime(dto.getStartTime());
        log.setEndTime(dto.getEndTime());
        log.setDescription(dto.getDescription());
        log.setSource("MANUAL");

        return mapTimeLog(timeLogRepository.save(log));
    }

    @Override
    public List<TimeLogDTO> getUserTimeLogs(String username) {
        User user = authorizationService.getRequiredUser(username);
        return timeLogRepository.findByUserId(user.getId()).stream()
                .sorted(Comparator.comparing(TimeLog::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .map(this::mapTimeLog)
                .toList();
    }

    public TimeLogDTO mapTimeLog(TimeLog log) {
        TimeLogDTO dto = new TimeLogDTO();
        dto.setId(log.getId());
        dto.setTaskId(log.getTask().getId());
        dto.setTaskTitle(log.getTask().getTitle());
        dto.setProjectId(log.getTask().getProject().getId());
        dto.setProjectName(log.getTask().getProject().getName());
        dto.setUserId(log.getUser().getId());
        dto.setUsername(log.getUser().getUsername());
        dto.setStartTime(log.getStartTime());
        dto.setEndTime(log.getEndTime());
        dto.setDescription(log.getDescription());
        dto.setSource(log.getSource());
        return dto;
    }
}
