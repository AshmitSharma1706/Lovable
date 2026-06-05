package com.project.lovable.entity;

import com.project.lovable.enums.MessageRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "chat_messages")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    })
    ChatSession chatSession;

    @Column(columnDefinition = "text", nullable = false)
    String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    MessageRole role;

    Integer tokensUsed=0;

    @CreationTimestamp
    Instant createdAt;
}
