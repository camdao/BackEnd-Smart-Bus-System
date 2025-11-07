package com.SmartBus.domain.router.dto.response;

import com.SmartBus.domain.router.domain.Router;

public record RouterResponse(
    Long id,
    String name,
    String startLocation,
    String endLocation,
    double distance
) {
    public static RouterResponse from(Router router) {
        return new RouterResponse(
            router.getId(),
            router.getName(),
            router.getStartLocation(),
            router.getEndLocation(),
            router.getDistance()
        );
    }
}