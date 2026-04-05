package com.rashid.backend.service;

import com.rashid.backend.dto.auth.AuthRequestDTO;
import com.rashid.backend.dto.auth.AuthResponseDTO;
import com.rashid.backend.dto.auth.RegisterRequestDTO;
import com.rashid.backend.model.enums.Role;
import com.rashid.backend.model.User;
import com.rashid.backend.exception.DuplicateResourceException;
import com.rashid.backend.exception.InvalidCredentialsException;
import com.rashid.backend.repository.UserRepository;
import com.rashid.backend.security.JwtUtil;
import com.rashid.backend.service.interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        String normalizedUsername = request.getUsername().trim();
        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);

        if (userRepository.findByUsername(normalizedUsername).isPresent()) {
            throw new DuplicateResourceException("Username is already taken");
        }

        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new DuplicateResourceException("Email is already in use");
        }

        User user = new User();
        user.setUsername(normalizedUsername);
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponseDTO(token, user.getUsername());
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

            String token = jwtUtil.generateToken(authentication.getName(), user.getRole().name());
            return new AuthResponseDTO(token, authentication.getName());
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
