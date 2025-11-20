package com.SmartBus.domain.chat.dto.request;


public record ChatMessage(
        Long chatRoomId,
        String content,
        String receiverUsername
) {
}