package com.project.lovable.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.project.lovable.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<StreamResponse> streamResponse(String message, Long projectId);
}
