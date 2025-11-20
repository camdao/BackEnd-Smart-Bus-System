package com.SmartBus.global.util;

import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return Long.parseLong(authentication.getName());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SAMPLE_ERROR);
        }
    }
}