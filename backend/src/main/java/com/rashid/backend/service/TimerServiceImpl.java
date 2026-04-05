package com.rashid.backend.service;

import com.rashid.backend.dto.timer.TimerSessionDTO;
import com.rashid.backend.dto.timer.TimerStartRequestDTO;
import com.rashid.backend.dto.timer.TimerStopRequestDTO;
import com.rashid.backend.dto.timelog.TimeLogDTO;
import com.rashid.backend.exception.BadRequestException;
import com.rashid.backend.exception.ResourceNotFoundException;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.TimerSession;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.repository.TimerSessionRepository;
import com.rashid.backend.service.interfaces.TimerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimerServiceImpl implements TimerService {

    private final TimerSessionRepository timerSessionRepository;
    private final TimeLogRepository timeLogRepository;
    private final AuthorizationService authorizationService;
    private final TimeLogServiceImpl timeLogService;

    public TimerServiceImpl(
            TimerSessionRepository timerSessionRepository,
            TimeLogRepository timeLogRepository,
            AuthorizationService authorizationService,
            TimeLogServiceImpl timeLogService
    ) {
        this.timerSessionRepository = timerSessionRepository;
        this.timeLogRepository = timeLogRepository;
        this.authorizationService = authorizationService;
        this.timeLogService = timeLogService;
    }

    @Override
    public TimerSessionDTO startTimer(TimerStartRequestDTO request, String username) {
        User user = authorizationService.getRequiredUser(username);
        if (timerSessionRepository.findByUserIdAndActiveTrue(user.getId()).isPresent()) {
            throw new BadRequestException("You already have an active timer");
        }

        Task task = authorizationService.getRequiredTask(request.getTaskId());
        authorizationService.requireTaskAccess(task, user.getId());

        TimerSession timer = new TimerSession();
        timer.setTask(task);
        timer.setUser(user);
        timer.setStartedAt(LocalDateTime.now());
        timer.setDescription(request.getDescription());
        timer.setActive(true);
        return mapTimer(timerSessionRepository.save(timer));
    }

    @Override
    public TimerSessionDTO getActiveTimer(String username) {
        User user = authorizationService.getRequiredUser(username);
        return timerSessionRepository.findByUserIdAndActiveTrue(user.getId())
                .map(this::mapTimer)
                .orElse(null);
    }

    @Override
    public TimeLogDTO stopTimer(Long timerId, TimerStopRequestDTO request, String username) {
        User user = authorizationService.getRequiredUser(username);
        TimerSession timer = getOwnedActiveTimer(timerId, user.getId());
        timer.setActive(false);
        timer.setStoppedAt(LocalDateTime.now());
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            timer.setDescription(request.getDescription().trim());
        }
        timerSessionRepository.save(timer);

        TimeLog log = new TimeLog();
        log.setTask(timer.getTask());
        log.setUser(user);
        log.setStartTime(timer.getStartedAt());
        log.setEndTime(timer.getStoppedAt());
        log.setDescription(timer.getDescription());
        log.setSource("TIMER");
        return timeLogService.mapTimeLog(timeLogRepository.save(log));
    }

    @Override
    public void cancelTimer(Long timerId, String username) {
        User user = authorizationService.getRequiredUser(username);
        TimerSession timer = getOwnedActiveTimer(timerId, user.getId());
        timer.setActive(false);
        timer.setStoppedAt(LocalDateTime.now());
        timerSessionRepository.save(timer);
    }

    private TimerSession getOwnedActiveTimer(Long timerId, Long userId) {
        TimerSession timer = timerSessionRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer not found"));
        if (!timer.getUser().getId().equals(userId)) {
            throw new BadRequestException("You cannot manage this timer");
        }
        if (!timer.isActive()) {
            throw new BadRequestException("This timer is no longer active");
        }
        return timer;
    }

    private TimerSessionDTO mapTimer(TimerSession timer) {
        TimerSessionDTO dto = new TimerSessionDTO();
        dto.setId(timer.getId());
        dto.setTaskId(timer.getTask().getId());
        dto.setTaskTitle(timer.getTask().getTitle());
        dto.setProjectId(timer.getTask().getProject().getId());
        dto.setProjectName(timer.getTask().getProject().getName());
        dto.setStartedAt(timer.getStartedAt());
        dto.setDescription(timer.getDescription());
        dto.setActive(timer.isActive());
        return dto;
    }
}
