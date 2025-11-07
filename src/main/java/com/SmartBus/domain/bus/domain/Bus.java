package com.SmartBus.domain.bus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long id;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BusStatus status;

    @Builder
    private Bus(String licensePlate, String model, int capacity, BusStatus status) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.capacity = capacity;
        this.status = status;
    }
    public static Bus createBus(String licensePlate, String model, int capacity, BusStatus status) {
        return Bus.builder()
                .licensePlate(licensePlate)
                .model(model)
                .capacity(capacity)
                .status(status)
                .build();
    }

    public void update(String licensePlate, String model, int capacity, BusStatus status) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.capacity = capacity;
        this.status = status;
    }
}