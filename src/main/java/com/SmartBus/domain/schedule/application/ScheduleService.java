package com.SmartBus.domain.schedule.application;


import com.SmartBus.domain.schedule.domain.Schedule;
import com.SmartBus.domain.schedule.dao.ScheduleRepository;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleCreateResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleFindAllResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleFindResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleUpdateResponse;
import com.SmartBus.domain.schedule.dto.request.ScheduleCreateRequest;
import com.SmartBus.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleCreateResponse createSchedule(ScheduleCreateRequest scheduleCreateRequest) {
        Schedule schedule =  scheduleRepository.save(Schedule.createSchedule(
                scheduleCreateRequest.bus(),
                scheduleCreateRequest.driver(),
                scheduleCreateRequest.router(),
                scheduleCreateRequest.scheduleDate(),
                scheduleCreateRequest.startTime(),
                scheduleCreateRequest.endTime()
        ));

        return ScheduleCreateResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleFindAllResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleFindAllResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleFindResponse getScheduleById(Long id) {
        Schedule schedule =  scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        return ScheduleFindResponse.from(schedule);
    }

   public ScheduleUpdateResponse updateSchedule(Long id, ScheduleUpdateRequest updatedSchedule) {
       Schedule schedule = scheduleRepository.findById(id)
               .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

       schedule.update(
               updatedSchedule.bus(),
               updatedSchedule.driver(),
               updatedSchedule.router(),
               updatedSchedule.scheduleDate(),
               updatedSchedule.startTime(),
               updatedSchedule.endTime()
       );

       return ScheduleUpdateResponse.from(schedule);
   }
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}