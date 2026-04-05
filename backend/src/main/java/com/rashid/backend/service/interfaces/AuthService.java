package com.rashid.backend.service.interfaces;

import com.rashid.backend.dto.auth.AuthRequestDTO;
import com.rashid.backend.dto.auth.AuthResponseDTO;
import com.rashid.backend.dto.auth.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO request);
}
