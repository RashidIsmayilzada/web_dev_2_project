package com.rashid.backend.repository;

import com.rashid.backend.model.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
    List<TimeLog> findByUserId(Long userId);
    List<TimeLog> findByTaskId(Long taskId);
    List<TimeLog> findByUserIdAndStartTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<TimeLog> findByTaskProjectTeamId(Long teamId);
    List<TimeLog> findByTaskProjectTeamIdAndStartTimeBetween(Long teamId, LocalDateTime start, LocalDateTime end);
}
