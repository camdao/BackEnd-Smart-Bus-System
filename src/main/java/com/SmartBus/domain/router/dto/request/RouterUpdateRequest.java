package com.SmartBus.domain.router.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RouterUpdateRequest(
    @NotBlank(message = "Name cannot be empty")
    String name,

    @NotBlank(message = "Start location cannot be empty")
    String startLocation,

    @NotBlank(message = "End location cannot be empty")
    String endLocation,

    @Positive(message = "Distance must be positive")
    double distance
) {}