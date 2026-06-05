package com.project.lovable.controller;

import com.project.lovable.dto.chat.ChatRequest;
import com.project.lovable.service.AiGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final AiGenerationService aiGenerationService;

    public Flux<ServerSentEvent<String>> streamChat(@RequestBody ChatRequest chatRequest){
        return aiGenerationService.streamResponse(chatRequest.message(), chatRequest.projectId())
                .map(data -> ServerSentEvent.<String>builder()
                        .data(data)
                        .build());
    }
}
