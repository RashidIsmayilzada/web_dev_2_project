package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.timelog.TimeLogDTO;

public interface TimeLogService {
    TimeLogDTO logTime(TimeLogDTO timeLogDTO, String username);
    PagedResponseDTO<TimeLogDTO> getUserTimeLogs(int page, int size, String username);
}
