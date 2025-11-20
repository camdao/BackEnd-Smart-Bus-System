package com.SmartBus.domain.chat.dao;

import com.SmartBus.domain.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomIdOrderByTimestamp(Long chatRoomId);
}