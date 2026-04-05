package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.team.TeamDTO;
import com.rashid.backend.dto.team.TeamInviteDTO;
import com.rashid.backend.dto.team.TeamInviteRequestDTO;
import com.rashid.backend.dto.team.TeamMemberDTO;
import com.rashid.backend.dto.team.TeamRoleUpdateDTO;

import java.util.List;

public interface TeamService {
    List<TeamDTO> getUserTeams(String username);
    TeamDTO createTeam(TeamDTO request, String username);
    TeamDTO updateTeam(Long teamId, TeamDTO request, String username);
    List<TeamMemberDTO> getTeamMembers(Long teamId, String username);
    TeamInviteDTO createInvite(Long teamId, TeamInviteRequestDTO request, String username);
    List<TeamInviteDTO> getTeamInvites(Long teamId, String username);
    List<TeamInviteDTO> getMyPendingInvites(String username);
    TeamInviteDTO acceptInvite(Long inviteId, String username);
    TeamInviteDTO declineInvite(Long inviteId, String username);
    TeamMemberDTO updateMemberRole(Long teamId, Long userId, TeamRoleUpdateDTO request, String username);
    void removeMember(Long teamId, Long userId, String username);
}
