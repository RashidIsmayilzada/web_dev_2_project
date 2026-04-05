package com.rashid.backend.service;

import com.rashid.backend.dto.report.WeeklySummaryDTO;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TimeLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private TimeLogRepository timeLogRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void weeklySummaryAggregatesHoursForCurrentWeek() {
        User user = new User();
        user.setId(1L);

        TimeLog log = new TimeLog();
        log.setUser(user);
        log.setTask(new Task());
        log.setStartTime(LocalDate.now().with(java.time.DayOfWeek.MONDAY).atTime(9, 0));
        log.setEndTime(LocalDate.now().with(java.time.DayOfWeek.MONDAY).atTime(11, 30));

        when(authorizationService.getRequiredUser("dev")).thenReturn(user);
        when(timeLogRepository.findByUserIdAndStartTimeBetween(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(log));

        WeeklySummaryDTO summary = reportService.getWeeklySummary("dev");

        assertEquals(2.5, summary.getTotalHours());
        assertEquals(1, summary.getEntriesCount());
    }
}
