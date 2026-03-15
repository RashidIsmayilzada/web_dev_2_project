package com.rashid.backend.repository;

import com.rashid.backend.model.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
    List<TimeLog> findByUserId(Long userId);
}
