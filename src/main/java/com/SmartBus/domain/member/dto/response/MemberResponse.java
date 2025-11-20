package com.SmartBus.domain.member.dto.response;

import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.member.domain.MemberRole;

public record MemberResponse(
    Long id,
    String username,
    MemberRole role
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getUsername(),
            member.getRole()
        );
    }
}