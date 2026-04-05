package com.rashid.backend.service;

import com.rashid.backend.dto.team.TeamDTO;
import com.rashid.backend.dto.team.TeamInviteDTO;
import com.rashid.backend.dto.team.TeamInviteRequestDTO;
import com.rashid.backend.dto.team.TeamMemberDTO;
import com.rashid.backend.dto.team.TeamRoleUpdateDTO;
import com.rashid.backend.exception.BadRequestException;
import com.rashid.backend.exception.DuplicateResourceException;
import com.rashid.backend.exception.ResourceNotFoundException;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TeamInvite;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.User;
import com.rashid.backend.model.enums.InviteStatus;
import com.rashid.backend.model.enums.TeamRole;
import com.rashid.backend.repository.TeamInviteRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TeamRepository;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.service.interfaces.TeamService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamInviteRepository teamInviteRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public TeamServiceImpl(
            TeamRepository teamRepository,
            TeamMemberRepository teamMemberRepository,
            TeamInviteRepository teamInviteRepository,
            UserRepository userRepository,
            AuthorizationService authorizationService
    ) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamInviteRepository = teamInviteRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public List<TeamDTO> getUserTeams(String username) {
        User user = authorizationService.getRequiredUser(username);

        return teamMemberRepository.findByUserId(user.getId()).stream()
                .map(member -> mapTeam(member.getTeam(), member.getRole()))
                .toList();
    }

    @Override
    public TeamDTO createTeam(TeamDTO request, String username) {
        User user = authorizationService.getRequiredUser(username);

        Team team = new Team();
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        team = teamRepository.save(team);

        TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setUser(user);
        member.setRole(TeamRole.OWNER);
        teamMemberRepository.save(member);

        return mapTeam(team, TeamRole.OWNER);
    }

    @Override
    public TeamDTO updateTeam(Long teamId, TeamDTO request, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireManagerOrOwner(teamId, user.getId());
        Team team = authorizationService.getRequiredTeam(teamId);

        if (request.getName() != null && !request.getName().isBlank()) {
            team.setName(request.getName().trim());
        }
        team.setDescription(request.getDescription());
        Team saved = teamRepository.save(team);
        TeamRole currentRole = teamMemberRepository.findByTeamIdAndUserId(teamId, user.getId())
                .map(TeamMember::getRole)
                .orElse(TeamRole.MEMBER);

        return mapTeam(saved, currentRole);
    }

    @Override
    public List<TeamMemberDTO> getTeamMembers(Long teamId, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireTeamMembership(teamId, user.getId());

        return teamMemberRepository.findByTeamId(teamId).stream()
                .map(this::mapMember)
                .toList();
    }

    @Override
    public TeamInviteDTO createInvite(Long teamId, TeamInviteRequestDTO request, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        authorizationService.requireManagerOrOwner(teamId, currentUser.getId());
        Team team = authorizationService.getRequiredTeam(teamId);

        User invitedUser = userRepository.findByUsername(request.getUsername().trim())
                .orElseThrow(() -> new ResourceNotFoundException("User to invite was not found"));

        if (invitedUser.getId().equals(currentUser.getId())) {
            throw new BadRequestException("You cannot invite yourself");
        }

        if (teamMemberRepository.findByTeamIdAndUserId(teamId, invitedUser.getId()).isPresent()) {
            throw new DuplicateResourceException("This user is already a team member");
        }

        if (teamInviteRepository.findByTeamIdAndInvitedUserIdAndStatus(teamId, invitedUser.getId(), InviteStatus.PENDING).isPresent()) {
            throw new DuplicateResourceException("There is already a pending invite for this user");
        }

        TeamInvite invite = new TeamInvite();
        invite.setTeam(team);
        invite.setInvitedUser(invitedUser);
        invite.setInvitedBy(currentUser);
        invite.setStatus(InviteStatus.PENDING);
        invite.setCreatedAt(LocalDateTime.now());
        return mapInvite(teamInviteRepository.save(invite));
    }

    @Override
    public List<TeamInviteDTO> getTeamInvites(Long teamId, String username) {
        User user = authorizationService.getRequiredUser(username);
        authorizationService.requireManagerOrOwner(teamId, user.getId());
        return teamInviteRepository.findByTeamIdOrderByCreatedAtDesc(teamId).stream()
                .map(this::mapInvite)
                .toList();
    }

    @Override
    public List<TeamInviteDTO> getMyPendingInvites(String username) {
        User user = authorizationService.getRequiredUser(username);
        return teamInviteRepository.findByInvitedUserIdAndStatusOrderByCreatedAtDesc(user.getId(), InviteStatus.PENDING).stream()
                .map(this::mapInvite)
                .toList();
    }

    @Override
    public TeamInviteDTO acceptInvite(Long inviteId, String username) {
        User user = authorizationService.getRequiredUser(username);
        TeamInvite invite = getPendingInviteForUser(inviteId, user.getId());

        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(invite.getTeam());
        teamMember.setUser(user);
        teamMember.setRole(TeamRole.MEMBER);
        teamMemberRepository.save(teamMember);

        invite.setStatus(InviteStatus.ACCEPTED);
        invite.setRespondedAt(LocalDateTime.now());
        return mapInvite(teamInviteRepository.save(invite));
    }

    @Override
    public TeamInviteDTO declineInvite(Long inviteId, String username) {
        User user = authorizationService.getRequiredUser(username);
        TeamInvite invite = getPendingInviteForUser(inviteId, user.getId());
        invite.setStatus(InviteStatus.DECLINED);
        invite.setRespondedAt(LocalDateTime.now());
        return mapInvite(teamInviteRepository.save(invite));
    }

    @Override
    public TeamMemberDTO updateMemberRole(Long teamId, Long userId, TeamRoleUpdateDTO request, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        authorizationService.requireOwner(teamId, currentUser.getId());

        TeamMember member = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));

        if (member.getRole() == TeamRole.OWNER && request.getRole() != TeamRole.OWNER) {
            throw new BadRequestException("Transfer of ownership is not supported");
        }

        member.setRole(request.getRole());
        return mapMember(teamMemberRepository.save(member));
    }

    @Override
    public void removeMember(Long teamId, Long userId, String username) {
        User currentUser = authorizationService.getRequiredUser(username);
        TeamMember actingMember = authorizationService.requireManagerOrOwner(teamId, currentUser.getId());
        TeamMember member = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));

        if (member.getRole() == TeamRole.OWNER) {
            throw new BadRequestException("The team owner cannot be removed");
        }

        if (actingMember.getRole() == TeamRole.MANAGER && member.getRole() == TeamRole.MANAGER) {
            throw new BadRequestException("Managers cannot remove other managers");
        }

        teamMemberRepository.delete(member);
    }

    private TeamInvite getPendingInviteForUser(Long inviteId, Long userId) {
        TeamInvite invite = teamInviteRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Invite not found"));

        if (!invite.getInvitedUser().getId().equals(userId)) {
            throw new BadRequestException("You cannot act on this invite");
        }

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new BadRequestException("This invite has already been responded to");
        }

        return invite;
    }

    private TeamDTO mapTeam(Team team, TeamRole currentRole) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setDescription(team.getDescription());
        dto.setCurrentUserRole(currentRole);
        dto.setMemberCount(teamMemberRepository.findByTeamId(team.getId()).size());
        dto.setPendingInviteCount((int) teamInviteRepository.findByTeamIdOrderByCreatedAtDesc(team.getId()).stream()
                .filter(invite -> invite.getStatus() == InviteStatus.PENDING)
                .count());
        return dto;
    }

    private TeamMemberDTO mapMember(TeamMember member) {
        TeamMemberDTO dto = new TeamMemberDTO();
        dto.setUserId(member.getUser().getId());
        dto.setUsername(member.getUser().getUsername());
        dto.setEmail(member.getUser().getEmail());
        dto.setRole(member.getRole());
        return dto;
    }

    private TeamInviteDTO mapInvite(TeamInvite invite) {
        TeamInviteDTO dto = new TeamInviteDTO();
        dto.setId(invite.getId());
        dto.setTeamId(invite.getTeam().getId());
        dto.setTeamName(invite.getTeam().getName());
        dto.setInvitedUserId(invite.getInvitedUser().getId());
        dto.setInvitedUsername(invite.getInvitedUser().getUsername());
        dto.setInvitedByUserId(invite.getInvitedBy().getId());
        dto.setInvitedByUsername(invite.getInvitedBy().getUsername());
        dto.setStatus(invite.getStatus());
        dto.setCreatedAt(invite.getCreatedAt());
        dto.setRespondedAt(invite.getRespondedAt());
        return dto;
    }
}
