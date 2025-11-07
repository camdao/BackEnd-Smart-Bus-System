package com.SmartBus.domain.schedule.dto.request;

import com.SmartBus.domain.bus.domain.Bus;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.router.domain.Router;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;


public record ScheduleUpdateRequest(
        @NotNull(message = "Bus cannot be empty")
        Bus bus,

        @NotNull(message = "Driver cannot be empty")
        Member driver,

        @NotNull(message = "Route cannot be empty")
        Router router,

        @NotNull(message = "Schedule date cannot be empty")
        LocalDate scheduleDate,

        @NotNull(message = "Start time cannot be empty")
        LocalTime startTime,

        @NotNull(message = "End time cannot be empty")
        LocalTime endTime
) {}
