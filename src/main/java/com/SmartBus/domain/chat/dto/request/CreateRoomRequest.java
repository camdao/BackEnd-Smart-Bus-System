package com.SmartBus.domain.chat.dto.request;

public record CreateRoomRequest(
        String name,
        String participantUsername
) {
}