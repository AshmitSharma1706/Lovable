package com.project.lovable.dto.auth;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {

}
