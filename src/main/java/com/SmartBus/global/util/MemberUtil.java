package com.SmartBus.global.util;

import com.SmartBus.domain.member.dao.MemberRepository;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final SecurityUtil securityUtil;
    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        return memberRepository
                .findById(securityUtil.getCurrentMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member getMemberByMemberId(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}