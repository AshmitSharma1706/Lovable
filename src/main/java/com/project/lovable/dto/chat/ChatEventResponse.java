package com.project.lovable.dto.chat;

import com.project.lovable.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String metadata,
        String filePath
) {
}
