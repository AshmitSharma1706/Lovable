package com.project.lovable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatSessionId  implements Serializable {
    Long projectId;
    Long userId;
}
