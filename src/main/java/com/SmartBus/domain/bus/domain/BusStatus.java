package com.SmartBus.domain.bus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusStatus {
    AVAILABLE("AVAILABLE"),
    IN_SERVICE("IN_SERVICE"),
    UNDER_MAINTENANCE("UNDER_MAINTENANCE");

    private final String value;

}