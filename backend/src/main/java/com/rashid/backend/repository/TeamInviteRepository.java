package com.rashid.backend.repository;

import com.rashid.backend.model.TeamInvite;
import com.rashid.backend.model.enums.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamInviteRepository extends JpaRepository<TeamInvite, Long> {
    List<TeamInvite> findByTeamIdOrderByCreatedAtDesc(Long teamId);
    List<TeamInvite> findByInvitedUserIdAndStatusOrderByCreatedAtDesc(Long invitedUserId, InviteStatus status);
    Optional<TeamInvite> findByTeamIdAndInvitedUserIdAndStatus(Long teamId, Long invitedUserId, InviteStatus status);
}
