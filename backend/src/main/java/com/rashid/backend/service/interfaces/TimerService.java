package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.timer.TimerSessionDTO;
import com.rashid.backend.dto.timer.TimerStartRequestDTO;
import com.rashid.backend.dto.timer.TimerStopRequestDTO;
import com.rashid.backend.dto.timelog.TimeLogDTO;

public interface TimerService {
    TimerSessionDTO startTimer(TimerStartRequestDTO request, String username);
    TimerSessionDTO getActiveTimer(String username);
    TimeLogDTO stopTimer(Long timerId, TimerStopRequestDTO request, String username);
    void cancelTimer(Long timerId, String username);
}
