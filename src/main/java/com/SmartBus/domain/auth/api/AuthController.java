package com.SmartBus.domain.auth.api;

import com.SmartBus.domain.auth.application.AuthService;
import com.SmartBus.domain.auth.dto.request.UsernamePasswordRequest;
import com.SmartBus.domain.auth.dto.response.TokenPairResponse;
import com.SmartBus.global.util.CookieUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;
    @PostMapping("/login")
    public ResponseEntity<TokenPairResponse> memberLogin(
            @Valid @RequestBody UsernamePasswordRequest request) {
        TokenPairResponse response = authService.loginMember(request);

        String accessToken = response.accessToken();
        String refreshToken = response.refreshToken();
        HttpHeaders tokenHeaders = cookieUtil.generateTokenCookies(accessToken, refreshToken);

        return ResponseEntity.ok().headers(tokenHeaders).body(response);
    }

}
