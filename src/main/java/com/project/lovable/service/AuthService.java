package com.project.lovable.service;

import com.project.lovable.dto.auth.AuthResponse;
import com.project.lovable.dto.auth.LoginRequest;
import com.project.lovable.dto.auth.SignupRequest;
import org.jspecify.annotations.Nullable;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
