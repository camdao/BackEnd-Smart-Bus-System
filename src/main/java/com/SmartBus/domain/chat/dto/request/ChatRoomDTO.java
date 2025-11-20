package com.SmartBus.domain.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomDTO {
    private Long chatRoomId;
    private String chatRoomName;
    private String otherMemberName;
}