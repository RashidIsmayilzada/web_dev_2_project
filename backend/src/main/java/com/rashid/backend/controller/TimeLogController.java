package com.rashid.backend.controller;

import com.rashid.backend.dto.TimeLogDTO;
import com.rashid.backend.service.TimeLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timelogs")
public class TimeLogController {

    private final TimeLogService timeLogService;

    public TimeLogController(TimeLogService timeLogService) {
        this.timeLogService = timeLogService;
    }

    @PostMapping
    public ResponseEntity<TimeLogDTO> logTime(@RequestBody TimeLogDTO timeLogDTO, Authentication authentication) {
        TimeLogDTO created = timeLogService.logTime(timeLogDTO, authentication.getName());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TimeLogDTO>> getUserTimeLogs(Authentication authentication) {
        List<TimeLogDTO> logs = timeLogService.getUserTimeLogs(authentication.getName());
        return ResponseEntity.ok(logs);
    }
}
