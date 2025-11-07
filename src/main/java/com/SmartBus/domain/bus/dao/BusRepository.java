package com.SmartBus.domain.bus.dao;

import com.SmartBus.domain.bus.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
