package com.SmartBus.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String username;

    private String password;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(
            MemberRole role,
            String username,
            String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public static Member createMember(String username, String password) {
        return Member.builder()
                .username(username)
                .password(password)
                .role(MemberRole.ADMIN)
                .build();
    }
}
