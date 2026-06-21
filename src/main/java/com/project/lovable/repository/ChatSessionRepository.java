package com.project.lovable.repository;

import com.project.lovable.entity.ChatSession;
import com.project.lovable.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {

}
