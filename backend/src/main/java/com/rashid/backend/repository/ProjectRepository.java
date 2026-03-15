package com.rashid.backend.repository;

import com.rashid.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    long countByTeamIdIn(List<Long> teamIds);
    List<Project> findByTeamId(Long teamId);
}
