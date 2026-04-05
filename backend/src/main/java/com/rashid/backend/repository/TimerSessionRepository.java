package com.rashid.backend.repository;

import com.rashid.backend.model.TimerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimerSessionRepository extends JpaRepository<TimerSession, Long> {
    Optional<TimerSession> findByUserIdAndActiveTrue(Long userId);
}
