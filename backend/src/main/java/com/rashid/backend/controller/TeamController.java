package com.rashid.backend.controller;

import com.rashid.backend.dto.common.PagedResponseDTO;
import com.rashid.backend.dto.team.TeamDTO;
import com.rashid.backend.dto.team.TeamInviteDTO;
import com.rashid.backend.dto.team.TeamInviteRequestDTO;
import com.rashid.backend.dto.team.TeamMemberDTO;
import com.rashid.backend.dto.team.TeamRoleUpdateDTO;
import com.rashid.backend.service.interfaces.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<TeamDTO>> getUserTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(teamService.getUserTeams(page, size, authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO, Authentication authentication) {
        return ResponseEntity.ok(teamService.createTeam(teamDTO, authentication.getName()));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable Long teamId,
            @RequestBody TeamDTO teamDTO,
            Authentication authentication
    ) {
        return ResponseEntity.ok(teamService.updateTeam(teamId, teamDTO, authentication.getName()));
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<PagedResponseDTO<TeamMemberDTO>> getTeamMembers(
            @PathVariable Long teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(teamService.getTeamMembers(teamId, page, size, authentication.getName()));
    }

    @PostMapping("/{teamId}/invites")
    public ResponseEntity<TeamInviteDTO> createInvite(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamInviteRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(teamService.createInvite(teamId, request, authentication.getName()));
    }

    @GetMapping("/{teamId}/invites")
    public ResponseEntity<PagedResponseDTO<TeamInviteDTO>> getTeamInvites(
            @PathVariable Long teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(teamService.getTeamInvites(teamId, page, size, authentication.getName()));
    }

    @GetMapping("/invites/me")
    public ResponseEntity<PagedResponseDTO<TeamInviteDTO>> getMyInvites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok(teamService.getMyPendingInvites(page, size, authentication.getName()));
    }

    @PostMapping("/invites/{inviteId}/accept")
    public ResponseEntity<TeamInviteDTO> acceptInvite(@PathVariable Long inviteId, Authentication authentication) {
        return ResponseEntity.ok(teamService.acceptInvite(inviteId, authentication.getName()));
    }

    @PostMapping("/invites/{inviteId}/decline")
    public ResponseEntity<TeamInviteDTO> declineInvite(@PathVariable Long inviteId, Authentication authentication) {
        return ResponseEntity.ok(teamService.declineInvite(inviteId, authentication.getName()));
    }

    @PutMapping("/{teamId}/members/{userId}/role")
    public ResponseEntity<TeamMemberDTO> updateMemberRole(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            @Valid @RequestBody TeamRoleUpdateDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(teamService.updateMemberRole(teamId, userId, request, authentication.getName()));
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long teamId, @PathVariable Long userId, Authentication authentication) {
        teamService.removeMember(teamId, userId, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
