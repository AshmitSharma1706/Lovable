package com.project.lovable.service.impl;

import com.project.lovable.dto.chat.ChatResponse;
import com.project.lovable.entity.ChatMessage;
import com.project.lovable.entity.ChatSession;
import com.project.lovable.entity.ChatSessionId;
import com.project.lovable.mapper.ChatMapper;
import com.project.lovable.repository.ChatMessageRepository;
import com.project.lovable.repository.ChatSessionRepository;
import com.project.lovable.security.AuthUtil;
import com.project.lovable.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final AuthUtil authUtil;
    private  final ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        Long userId= authUtil.getCurrentUserId();
        ChatSession chatSession= chatSessionRepository.getReferenceById(
                new ChatSessionId(projectId, userId)
        );
        List<ChatMessage> chatMessageList=chatMessageRepository.findByChatSession(chatSession);

        return chatMapper.fromListOfChatMessage(chatMessageList);
    }
}
