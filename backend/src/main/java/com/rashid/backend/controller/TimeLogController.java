package com.rashid.backend.controller;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.timelog.TimeLogDTO;
import com.rashid.backend.service.interfaces.TimeLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PagedResponseDTO<TimeLogDTO>> getUserTimeLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(timeLogService.getUserTimeLogs(page, size, authentication.getName()));
    }
}
