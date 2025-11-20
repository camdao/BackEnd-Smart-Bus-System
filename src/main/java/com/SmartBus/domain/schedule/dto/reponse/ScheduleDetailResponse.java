package com.SmartBus.domain.schedule.dto.reponse;
import com.SmartBus.domain.bus.domain.Bus;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.router.domain.Router;
import com.SmartBus.domain.schedule.domain.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDetailResponse(
        Long scheduleId,

        Bus bus,

        Router router,

        DriverResponse driver,

        LocalDate scheduleDate,

        LocalTime startTime,

        LocalTime endTime
) {


    public static ScheduleDetailResponse from(Schedule schedule) {
        return new ScheduleDetailResponse(
                schedule.getId(),
                schedule.getBus(),
                schedule.getRouter(),
                DriverResponse.from(schedule.getDriver()),
                schedule.getScheduleDate(),
                schedule.getStartTime(),
                schedule.getEndTime()
        );
    }
}