package com.project.lovable.service.impl;

import com.project.lovable.service.AiGenerationService;
import reactor.core.publisher.Flux;

public class AiGenerationServiceImpl implements AiGenerationService {
    @Override
    public Flux<String> streamResponse(String message, Long projectId) {
        return null;
    }
}
