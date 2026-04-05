package com.rashid.backend.service;

import com.rashid.backend.dto.TimeLogDTO;
import java.util.List;

public interface TimeLogService {
    TimeLogDTO logTime(TimeLogDTO timeLogDTO, String username);
    List<TimeLogDTO> getUserTimeLogs(String username);
}
