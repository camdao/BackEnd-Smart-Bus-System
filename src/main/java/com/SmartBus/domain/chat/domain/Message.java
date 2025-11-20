package com.SmartBus.domain.chat.domain;

import com.SmartBus.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private Member sender;

    private String content;

    private LocalDateTime timestamp;
}