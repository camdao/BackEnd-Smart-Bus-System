package com.SmartBus.domain.bus.api;



import com.SmartBus.domain.bus.application.BusService;
import com.SmartBus.domain.bus.dto.request.BusCreateRequest;
import com.SmartBus.domain.bus.dto.request.BusUpdateRequest;
import com.SmartBus.domain.bus.dto.response.BusResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;

    @PostMapping
    public ResponseEntity<BusResponse> createBus(@Valid @RequestBody BusCreateRequest request) {
        return ResponseEntity.ok(busService.createBus(request));
    }

    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Long id) {
        return ResponseEntity.ok(busService.getBusById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusResponse> updateBus(
            @PathVariable Long id,
            @Valid @RequestBody BusUpdateRequest request
    ) {
        return ResponseEntity.ok(busService.updateBus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }
}