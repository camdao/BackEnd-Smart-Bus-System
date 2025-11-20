package com.SmartBus.domain.chat.api;

import com.SmartBus.domain.chat.domain.ChatRoom;
import com.SmartBus.domain.chat.domain.Message;
import com.SmartBus.domain.chat.application.ChatService;
import com.SmartBus.domain.chat.dto.request.ChatMessage;
import com.SmartBus.domain.chat.dto.request.ChatRoomDTO;
import com.SmartBus.domain.chat.dto.request.CreateRoomRequest;
import com.SmartBus.domain.chat.dto.response.ChatRoomResponse;
import com.SmartBus.domain.chat.dto.response.MessageResponse;
import com.SmartBus.domain.member.dao.MemberRepository;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MemberRepository memberRepository;
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage chatMessage, Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        String receiver = chatMessage.receiverUsername();
        Message message = chatService.sendMessage(chatMessage.chatRoomId(), member.getUsername(), chatMessage.content());
        messagingTemplate.convertAndSendToUser(receiver, "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(member.getUsername(), "/queue/messages", message);
    }

   @GetMapping("/history/{chatRoomId}")
   public List<MessageResponse> getChatHistory(@PathVariable Long chatRoomId) {
       return chatService.getChatHistory(chatRoomId);
   }

   @PostMapping("/room")
   public ChatRoomResponse createChatRoom(@RequestBody CreateRoomRequest request) {
       return chatService.findChatRoomBetweenMembers(request.name(),  request.participantUsername());
   }
    @GetMapping("/rooms/member")
    public List<ChatRoomDTO> getAllChatRoomsByMember() {
        return chatService.getAllChatRoomsByMember();
    }
}