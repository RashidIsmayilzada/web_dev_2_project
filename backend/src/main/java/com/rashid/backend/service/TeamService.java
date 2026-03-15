package com.rashid.backend.service;

import com.rashid.backend.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    List<TeamDTO> getUserTeams(String username);
    TeamDTO createTeam(TeamDTO request, String username);
}
