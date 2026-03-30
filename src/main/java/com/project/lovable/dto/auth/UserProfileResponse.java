package com.project.lovable.dto.auth;

public record UserProfileResponse(
        Long id,
        String username,
        String name
) {
}
