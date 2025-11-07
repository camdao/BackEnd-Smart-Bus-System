package com.SmartBus.domain.router.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Router {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "router_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "start_location", nullable = false)
    private String startLocation;

    @Column(name = "end_location", nullable = false)
    private String endLocation;

    @Column(name = "distance", nullable = false)
    private double distance;

    @Builder
    private Router(String name, String startLocation, String endLocation, double distance) {
        this.name = name;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
    }
    public static Router createRouter(String name, String startLocation, String endLocation, double distance) {
        return Router.builder()
                .name(name)
                .startLocation(startLocation)
                .endLocation(endLocation)
                .distance(distance)
                .build();
    }
}