package com.SmartBus.domain.chat.application;


import com.SmartBus.domain.chat.dao.ChatRoomRepository;
import com.SmartBus.domain.chat.dao.MessageRepository;
import com.SmartBus.domain.chat.domain.ChatRoom;
import com.SmartBus.domain.chat.domain.Message;
import com.SmartBus.domain.chat.dto.request.ChatRoomDTO;
import com.SmartBus.domain.chat.dto.response.ChatRoomResponse;
import com.SmartBus.domain.chat.dto.response.MessageResponse;
import com.SmartBus.domain.member.dao.MemberRepository;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import com.SmartBus.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;
    public Message sendMessage(Long chatRoomId, String sender, String content) {
        Member senderMember = memberRepository.findByUsername(sender)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found"));

        Message message = new Message();
        message.setChatRoom(chatRoom);
        message.setSender(senderMember);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

   public List<MessageResponse> getChatHistory(Long chatRoomId) {
        Member currentUser = memberUtil.getCurrentMember();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found"));

        if (!chatRoom.getMember1().equals(currentUser) && !chatRoom.getMember2().equals(currentUser)) {
            throw new SecurityException("You are not allowed to access this chat room");
        }

        return messageRepository.findByChatRoomIdOrderByTimestamp(chatRoomId).stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        message.getSender().getUsername(),
                        message.getContent(),
                        message.getTimestamp()
                ))
                .toList();
    }

    public ChatRoom createChatRoom(String name, Member member1, Member member2) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setMember1(member1);
        chatRoom.setMember2(member2);

        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoomResponse findChatRoomBetweenMembers(String name , String member2Username) {
        Member member1 = memberUtil.getCurrentMember();
        Member member2 = memberRepository.findByUsername(member2Username)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        ChatRoom existingChatRoom = chatRoomRepository.findByMembers(member1, member2).orElse(null);
        if (existingChatRoom != null) {
            return ChatRoomResponse.of(existingChatRoom.getId(), existingChatRoom.getName(),
                    existingChatRoom.getMember1().equals(member1) ? member2.getUsername() : member1.getUsername());
        } else {
            return ChatRoomResponse.of(createChatRoom(name, member1, member2).getId(), name, member2.getUsername());
        }

    }



    public List<ChatRoomDTO> getAllChatRoomsByMember() {
        Member currentUser = memberUtil.getCurrentMember();
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByMember(currentUser);

        return chatRooms.stream().map(chatRoom -> {
            String otherMemberName = chatRoom.getMember1().equals(currentUser)
                    ? chatRoom.getMember2().getUsername()
                    : chatRoom.getMember1().getUsername();
            return new ChatRoomDTO(chatRoom.getId(), chatRoom.getName(), otherMemberName);
        }).toList();
    }
}