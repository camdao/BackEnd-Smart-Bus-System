package com.SmartBus.domain.auth.application;

import com.SmartBus.domain.auth.dto.request.UsernamePasswordRequest;
import com.SmartBus.domain.auth.dto.response.TokenPairResponse;
import com.SmartBus.domain.member.dao.MemberRepository;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    public TokenPairResponse loginMember(UsernamePasswordRequest request) {
        final Member member =
                memberRepository
                        .findByUsername(request.username())
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        validatePasswordMatches(member, request.password());

        return getLoginResponse(member);
    }
    private TokenPairResponse getLoginResponse(Member member) {
        String accessToken = jwtTokenService.createAccessToken(member.getId(), member.getRole());
        String refreshToken = jwtTokenService.createRefreshToken(member.getId());

        return TokenPairResponse.from(accessToken, refreshToken);
    }
    private void validatePasswordMatches(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHES);
        }
    }

    public void registerMember(UsernamePasswordRequest request) {
        if (memberRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = Member.createMember(request.username(), encodedPassword);

        memberRepository.save(member);
    }

}
