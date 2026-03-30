package com.project.lovable.mapper;

import com.project.lovable.dto.auth.SignupRequest;
import com.project.lovable.dto.auth.UserProfileResponse;
import com.project.lovable.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SignupRequest signupRequest);
    UserProfileResponse toUserProfileResponse(User user);
}
