package com.SmartBus.domain.schedule.dao;

import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
