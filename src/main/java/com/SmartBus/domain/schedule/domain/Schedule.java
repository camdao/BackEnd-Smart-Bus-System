package com.SmartBus.domain.schedule.domain;

import com.SmartBus.domain.bus.domain.Bus;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.router.domain.Router;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Member driver;

    @ManyToOne
    @JoinColumn(name = "router_id", nullable = false)
    private Router router;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Builder
    private Schedule(Bus bus, Member driver, Router router, LocalDate scheduleDate, LocalTime startTime, LocalTime endTime) {
        this.bus = bus;
        this.driver = driver;
        this.router = router;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Schedule createSchedule(Bus bus, Member driver, Router router, LocalDate scheduleDate, LocalTime startTime, LocalTime endTime) {
        return Schedule.builder()
                .bus(bus)
                .driver(driver)
                .router(router)
                .scheduleDate(scheduleDate)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}