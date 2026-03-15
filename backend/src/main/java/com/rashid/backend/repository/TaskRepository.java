package com.rashid.backend.repository;

import com.rashid.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByAssigneeIdAndStatus(Long assigneeId, String status);
    List<Task> findByProjectId(Long projectId);
}
