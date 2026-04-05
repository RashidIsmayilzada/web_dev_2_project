package com.rashid.backend.service;

import com.rashid.backend.dto.timer.TimerStartRequestDTO;
import com.rashid.backend.exception.BadRequestException;
import com.rashid.backend.model.TimerSession;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.repository.TimerSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimerServiceImplTest {

    @Mock
    private TimerSessionRepository timerSessionRepository;

    @Mock
    private TimeLogRepository timeLogRepository;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private TimeLogServiceImpl timeLogService;

    @InjectMocks
    private TimerServiceImpl timerService;

    @Test
    void startTimerRejectsSecondActiveTimer() {
        User user = new User();
        user.setId(1L);

        when(authorizationService.getRequiredUser("dev")).thenReturn(user);
        when(timerSessionRepository.findByUserIdAndActiveTrue(1L)).thenReturn(Optional.of(new TimerSession()));

        TimerStartRequestDTO request = new TimerStartRequestDTO();
        request.setTaskId(99L);

        assertThrows(BadRequestException.class, () -> timerService.startTimer(request, "dev"));
    }
}
