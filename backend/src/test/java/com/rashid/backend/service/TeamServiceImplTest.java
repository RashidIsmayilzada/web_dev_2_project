package com.rashid.backend.service;

import com.rashid.backend.dto.team.TeamInviteRequestDTO;
import com.rashid.backend.exception.DuplicateResourceException;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.User;
import com.rashid.backend.model.enums.TeamRole;
import com.rashid.backend.repository.TeamInviteRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TeamRepository;
import com.rashid.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private TeamInviteRepository teamInviteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    void createInviteRejectsExistingTeamMember() {
        User actingUser = new User();
        actingUser.setId(1L);
        actingUser.setUsername("owner");

        Team team = new Team();
        team.setId(10L);

        User invitedUser = new User();
        invitedUser.setId(2L);
        invitedUser.setUsername("member");

        TeamMember existingMember = new TeamMember();
        existingMember.setRole(TeamRole.MEMBER);

        TeamInviteRequestDTO request = new TeamInviteRequestDTO();
        request.setUsername("member");

        when(authorizationService.getRequiredUser("owner")).thenReturn(actingUser);
        when(authorizationService.getRequiredTeam(10L)).thenReturn(team);
        when(userRepository.findByUsername("member")).thenReturn(Optional.of(invitedUser));
        when(teamMemberRepository.findByTeamIdAndUserId(10L, 2L)).thenReturn(Optional.of(existingMember));

        assertThrows(DuplicateResourceException.class, () -> teamService.createInvite(10L, request, "owner"));
    }
}
