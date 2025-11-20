package com.SmartBus.domain.chat.dto.response;
import java.time.LocalDateTime;

public record MessageResponse(
        Long messageId,
        String senderUsername,
        String content,
        LocalDateTime timestamp
) {
}