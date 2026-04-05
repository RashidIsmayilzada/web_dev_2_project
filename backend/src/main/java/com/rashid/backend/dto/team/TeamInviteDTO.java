package com.rashid.backend.dto.team;

import com.rashid.backend.model.enums.InviteStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamInviteDTO {
    private Long id;
    private Long teamId;
    private String teamName;
    private Long invitedUserId;
    private String invitedUsername;
    private Long invitedByUserId;
    private String invitedByUsername;
    private InviteStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;
}
