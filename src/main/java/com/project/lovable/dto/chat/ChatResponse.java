package com.project.lovable.dto.chat;

import com.project.lovable.entity.ChatEvent;
import com.project.lovable.entity.ChatSession;
import com.project.lovable.enums.MessageRole;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        ChatSession chatSession,
        List<ChatEventResponse>events,
        String content,
        MessageRole role,
        Integer tokensUsed,
        Instant createdAt
) {

}
