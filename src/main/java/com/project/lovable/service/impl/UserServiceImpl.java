package com.project.lovable.service.impl;

import com.project.lovable.dto.auth.UserProfileResponse;
import com.project.lovable.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }
}
