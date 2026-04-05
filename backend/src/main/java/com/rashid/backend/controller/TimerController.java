package com.rashid.backend.controller;

import com.rashid.backend.dto.timer.TimerSessionDTO;
import com.rashid.backend.dto.timer.TimerStartRequestDTO;
import com.rashid.backend.dto.timer.TimerStopRequestDTO;
import com.rashid.backend.dto.timelog.TimeLogDTO;
import com.rashid.backend.service.interfaces.TimerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timers")
public class TimerController {

    private final TimerService timerService;

    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    @PostMapping("/start")
    public ResponseEntity<TimerSessionDTO> startTimer(@Valid @RequestBody TimerStartRequestDTO request, Authentication authentication) {
        return ResponseEntity.ok(timerService.startTimer(request, authentication.getName()));
    }

    @GetMapping("/active")
    public ResponseEntity<TimerSessionDTO> getActiveTimer(Authentication authentication) {
        return ResponseEntity.ok(timerService.getActiveTimer(authentication.getName()));
    }

    @PostMapping("/{timerId}/stop")
    public ResponseEntity<TimeLogDTO> stopTimer(
            @PathVariable Long timerId,
            @RequestBody(required = false) TimerStopRequestDTO request,
            Authentication authentication
    ) {
        TimerStopRequestDTO payload = request != null ? request : new TimerStopRequestDTO();
        return ResponseEntity.ok(timerService.stopTimer(timerId, payload, authentication.getName()));
    }

    @PostMapping("/{timerId}/cancel")
    public ResponseEntity<Void> cancelTimer(@PathVariable Long timerId, Authentication authentication) {
        timerService.cancelTimer(timerId, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
