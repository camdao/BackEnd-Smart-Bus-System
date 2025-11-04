package com.SmartBus.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record UsernamePasswordRequest(
        @NotNull(message = "Username cannot be null")
        String username,
        @NotNull(message = "Password cannot be null")
        String password) {}