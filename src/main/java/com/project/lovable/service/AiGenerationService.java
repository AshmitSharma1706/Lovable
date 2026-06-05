package com.project.lovable.service;

import aj.org.objectweb.asm.commons.Remapper;
import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<String> streamResponse(String message, Long projectId);
}
