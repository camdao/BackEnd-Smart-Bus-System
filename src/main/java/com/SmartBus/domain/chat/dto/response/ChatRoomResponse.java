package com.SmartBus.domain.chat.dto.response;

public record ChatRoomResponse (
    Long chatRoomId,
    String chatRoomName,
    String otherMemberName
){

    public static ChatRoomResponse of(Long id, String name, String s) {
        return new ChatRoomResponse(id, name, s);
    }
}
