package com.SmartBus.domain.auth.dto;


import com.SmartBus.domain.member.domain.MemberRole;

public record AccessTokenDto(Long memberId, MemberRole memberRole, String tokenValue) {}
