package com.SmartBus.domain.chat.domain;

import com.SmartBus.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Member member1;

    @ManyToOne
    private Member member2;
}