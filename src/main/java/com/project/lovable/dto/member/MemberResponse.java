package com.project.lovable.dto.member;

import com.project.lovable.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        ProjectRole role,
        Instant invitedAt
) {
}
