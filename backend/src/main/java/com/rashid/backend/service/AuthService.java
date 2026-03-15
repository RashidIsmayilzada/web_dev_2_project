package com.rashid.backend.service;

import com.rashid.backend.dto.AuthRequest;
import com.rashid.backend.dto.AuthResponse;
import com.rashid.backend.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
