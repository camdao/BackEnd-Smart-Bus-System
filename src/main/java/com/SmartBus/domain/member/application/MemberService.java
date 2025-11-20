package com.SmartBus.domain.member.application;


import com.SmartBus.domain.member.dao.MemberRepository;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.member.domain.MemberRole;
import com.SmartBus.domain.member.dto.response.MemberResponse;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberResponse> getAllDrivers() {
        return memberRepository.findByRole(MemberRole.DRIVER)
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public List<MemberResponse> getAllNonAdmins() {
        return memberRepository.findByRoleNot(MemberRole.ADMIN)
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse getMembersById(Long id) {
        Member member =  memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberResponse.from(member);
    }
}