package com.SmartBus.domain.schedule.dto.reponse;
import com.SmartBus.domain.schedule.domain.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleFindAllResponse(
        Long scheduleId,

        Long busId,

        Long driverId,

        Long routerId,

        LocalDate scheduleDate,

        LocalTime startTime,

        LocalTime endTime
) {
    public static ScheduleFindAllResponse from(Schedule schedule) {
        return new ScheduleFindAllResponse(
                schedule.getId(),
                schedule.getBus().getId(),
                schedule.getDriver().getId(),
                schedule.getRouter().getId(),
                schedule.getScheduleDate(),
                schedule.getStartTime(),
                schedule.getEndTime()
        );
    }
}