package com.SmartBus.global.security;

import com.SmartBus.domain.member.domain.MemberRole;
import com.SmartBus.domain.auth.application.JwtTokenService;
import com.SmartBus.domain.auth.dto.AccessTokenDto;
import com.SmartBus.domain.auth.dto.RefreshTokenDto;
import com.SmartBus.global.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;

import static com.SmartBus.global.common.constants.SecurityConstants.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final CookieUtil cookieUtil;

    private static String extractAccessTokenFromHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(TOKEN_PREFIX))
                .map(header -> header.replace(TOKEN_PREFIX, ""))
                .orElse(null);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessTokenHeaderValue = extractAccessTokenFromHeader(request);
        String accessTokenValue = extractAccessTokenFromCookie(request);
        String refreshTokenValue = extractRefreshTokenFromCookie(request);

        if (accessTokenHeaderValue != null) {
            AccessTokenDto accessTokenDto =
                    jwtTokenService.retrieveAccessToken(accessTokenHeaderValue);
            if (accessTokenDto != null) {
                setAuthenticationToContext(accessTokenDto.memberId(), accessTokenDto.memberRole());
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (accessTokenValue == null || refreshTokenValue == null) {
            filterChain.doFilter(request, response);
            return;
        }

        AccessTokenDto accessTokenDto = jwtTokenService.retrieveAccessToken(accessTokenValue);

        if (accessTokenDto != null) {
            setAuthenticationToContext(accessTokenDto.memberId(), accessTokenDto.memberRole());
            filterChain.doFilter(request, response);
            return;
        }

        Optional<AccessTokenDto> reissuedAccessToken =
                Optional.ofNullable(jwtTokenService.reissueAccessTokenIfExpired(accessTokenValue));
        RefreshTokenDto refreshTokenDto = jwtTokenService.retrieveRefreshToken(refreshTokenValue);

        if (reissuedAccessToken.isPresent() && refreshTokenDto != null) {
            AccessTokenDto accessToken = reissuedAccessToken.get();
            RefreshTokenDto refreshToken =
                    jwtTokenService.createRefreshTokenDto(refreshTokenDto.memberId());

            HttpHeaders httpHeaders =
                    cookieUtil.generateTokenCookies(
                            accessToken.tokenValue(), refreshToken.tokenValue());
            response.addHeader(
                    HttpHeaders.SET_COOKIE, httpHeaders.getFirst(ACCESS_TOKEN_COOKIE_NAME));
            response.addHeader(
                    HttpHeaders.SET_COOKIE, httpHeaders.getFirst(REFRESH_TOKEN_COOKIE_NAME));

            setAuthenticationToContext(accessToken.memberId(), accessToken.memberRole());
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(Long memberId, MemberRole memberRole) {
        UserDetails userDetails = new PrincipalDetails(memberId, memberRole.toString());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        return Optional.ofNullable(WebUtils.getCookie(request, ACCESS_TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .orElse(null);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        return Optional.ofNullable(WebUtils.getCookie(request, REFRESH_TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .orElse(null);
    }
}
