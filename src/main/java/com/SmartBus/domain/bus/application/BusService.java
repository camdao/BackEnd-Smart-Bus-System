package com.SmartBus.domain.bus.application;


import com.SmartBus.domain.bus.dao.BusRepository;
import com.SmartBus.domain.bus.domain.Bus;
import com.SmartBus.domain.bus.dto.request.BusCreateRequest;
import com.SmartBus.domain.bus.dto.request.BusUpdateRequest;
import com.SmartBus.domain.bus.dto.response.BusResponse;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;

    @Transactional
    public BusResponse createBus(BusCreateRequest request) {
        Bus bus = Bus.createBus(
            request.licensePlate(),
            request.model(),
            request.capacity(),
            request.status()
        );
        return BusResponse.from(busRepository.save(bus));
    }

    public List<BusResponse> getAllBuses() {
        return busRepository.findAll().stream()
                .map(BusResponse::from)
                .toList();
    }

    public BusResponse getBusById(Long id) {
        return busRepository.findById(id)
                .map(BusResponse::from)
                .orElseThrow(() -> new CustomException(ErrorCode.BUS_NOT_FOUND));
    }

    @Transactional
    public BusResponse updateBus(Long id, BusUpdateRequest request) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUS_NOT_FOUND));

        bus.update(
            request.licensePlate(),
            request.model(),
            request.capacity(),
            request.status()
        );
        return BusResponse.from(busRepository.save(bus));
    }

    @Transactional
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
}