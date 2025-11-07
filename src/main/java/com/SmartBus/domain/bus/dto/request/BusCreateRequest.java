package com.SmartBus.domain.bus.dto.request;


import com.SmartBus.domain.bus.domain.Bus;
import com.SmartBus.domain.bus.domain.BusStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BusCreateRequest(
    @NotBlank(message = "License plate cannot be empty")
    String licensePlate,

    @NotBlank(message = "Model cannot be empty")
    String model,

    @Positive(message = "Capacity must be positive")
    int capacity,

    @NotNull(message = "Status cannot be empty")
    BusStatus status
) {
}