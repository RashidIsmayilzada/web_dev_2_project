package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.team.TeamDTO;
import com.rashid.backend.dto.team.TeamInviteDTO;
import com.rashid.backend.dto.team.TeamInviteRequestDTO;
import com.rashid.backend.dto.team.TeamMemberDTO;
import com.rashid.backend.dto.team.TeamRoleUpdateDTO;

public interface TeamService {
    PagedResponseDTO<TeamDTO> getUserTeams(int page, int size, String username);
    TeamDTO createTeam(TeamDTO request, String username);
    TeamDTO updateTeam(Long teamId, TeamDTO request, String username);
    PagedResponseDTO<TeamMemberDTO> getTeamMembers(Long teamId, int page, int size, String username);
    TeamInviteDTO createInvite(Long teamId, TeamInviteRequestDTO request, String username);
    PagedResponseDTO<TeamInviteDTO> getTeamInvites(Long teamId, int page, int size, String username);
    PagedResponseDTO<TeamInviteDTO> getMyPendingInvites(int page, int size, String username);
    TeamInviteDTO acceptInvite(Long inviteId, String username);
    TeamInviteDTO declineInvite(Long inviteId, String username);
    TeamMemberDTO updateMemberRole(Long teamId, Long userId, TeamRoleUpdateDTO request, String username);
    void removeMember(Long teamId, Long userId, String username);
}
