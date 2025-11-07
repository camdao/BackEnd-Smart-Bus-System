package com.SmartBus.domain.schedule.api;

import com.SmartBus.domain.schedule.application.ScheduleService;
import com.SmartBus.domain.schedule.domain.Schedule;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleCreateResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleFindAllResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleFindResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleUpdateResponse;
import com.SmartBus.domain.schedule.dto.request.ScheduleCreateRequest;
import com.SmartBus.domain.schedule.dto.request.ScheduleUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleCreateResponse> createSchedule(@Valid @RequestBody ScheduleCreateRequest schedule) {
        ScheduleCreateResponse createdSchedule = scheduleService.createSchedule(schedule);
        return ResponseEntity.ok(createdSchedule);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleFindAllResponse>> getAllSchedules() {
        List<ScheduleFindAllResponse> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleFindResponse> getScheduleById(@PathVariable Long id) {
        ScheduleFindResponse schedule =  scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequest updatedSchedule
    ) {
        ScheduleUpdateResponse schedule = scheduleService.updateSchedule(id, updatedSchedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}