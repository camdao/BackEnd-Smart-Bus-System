package com.SmartBus.domain.schedule.dao;

import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.schedule.domain.Schedule;
import com.SmartBus.domain.schedule.dto.reponse.RouterMapResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s " +
            "WHERE s.scheduleDate = :today " +
            "AND s.startTime <= :currentTime " +
            "AND s.endTime >= :currentTime")
    List<Schedule> findRunningSchedules(LocalDate today, LocalTime currentTime);
}
