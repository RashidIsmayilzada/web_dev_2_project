package com.rashid.backend.controller;

import com.rashid.backend.dto.TeamDTO;
import com.rashid.backend.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getUserTeams(Authentication authentication) {
        return ResponseEntity.ok(teamService.getUserTeams(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO, Authentication authentication) {
        return ResponseEntity.ok(teamService.createTeam(teamDTO, authentication.getName()));
    }
}
