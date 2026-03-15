package com.rashid.backend.service.impl;

import com.rashid.backend.dto.TeamDTO;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.TeamRole;
import com.rashid.backend.model.User;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TeamRepository;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TeamDTO> getUserTeams(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return teamMemberRepository.findByUserId(user.getId()).stream()
                .map(member -> {
                    TeamDTO dto = new TeamDTO();
                    dto.setId(member.getTeam().getId());
                    dto.setName(member.getTeam().getName());
                    dto.setDescription(member.getTeam().getDescription());
                    dto.setCurrentUserRole(member.getRole());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public TeamDTO createTeam(TeamDTO request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team team = new Team();
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        team = teamRepository.save(team);

        TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setUser(user);
        member.setRole(TeamRole.OWNER);
        teamMemberRepository.save(member);

        TeamDTO response = new TeamDTO();
        response.setId(team.getId());
        response.setName(team.getName());
        response.setDescription(team.getDescription());
        response.setCurrentUserRole(TeamRole.OWNER);
        return response;
    }
}
