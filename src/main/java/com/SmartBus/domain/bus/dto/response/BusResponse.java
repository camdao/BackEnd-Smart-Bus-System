package com.SmartBus.domain.bus.dto.response;

import com.SmartBus.domain.bus.domain.Bus;
import com.SmartBus.domain.bus.domain.BusStatus;

public record BusResponse(
    Long id,

    String licensePlate,

    String model,

    int capacity,

    BusStatus status
) {
    public static BusResponse from(Bus bus) {
        return new BusResponse(
            bus.getId(),
            bus.getLicensePlate(),
            bus.getModel(),
            bus.getCapacity(),
            bus.getStatus()
        );
    }
}