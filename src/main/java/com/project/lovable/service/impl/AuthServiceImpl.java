package com.project.lovable.service.impl;

import com.project.lovable.dto.auth.AuthResponse;
import com.project.lovable.dto.auth.LoginRequest;
import com.project.lovable.dto.auth.SignupRequest;
import com.project.lovable.entity.User;
import com.project.lovable.error.BadRequestException;
import com.project.lovable.mapper.UserMapper;
import com.project.lovable.repository.UserRepository;
import com.project.lovable.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw  new BadRequestException("User already exists with username: "+request.username());
        });

        User user=userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user= userRepository.save(user);
        return new AuthResponse("dummy", userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
