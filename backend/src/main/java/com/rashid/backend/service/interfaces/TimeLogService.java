package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.timelog.TimeLogDTO;
import java.util.List;

public interface TimeLogService {
    TimeLogDTO logTime(TimeLogDTO timeLogDTO, String username);
    List<TimeLogDTO> getUserTimeLogs(String username);
}
