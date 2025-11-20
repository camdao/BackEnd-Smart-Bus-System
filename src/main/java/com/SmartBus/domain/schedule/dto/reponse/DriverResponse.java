package com.SmartBus.domain.schedule.dto.reponse;

import com.SmartBus.domain.member.domain.Member;

public record DriverResponse(
        Long id,
        String username
) {
    public static DriverResponse from(Member member) {
        return new DriverResponse(
                member.getId(),
                member.getUsername()
        );
    }
}
