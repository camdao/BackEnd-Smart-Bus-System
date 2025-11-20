package com.SmartBus.domain.schedule.application;


import com.SmartBus.domain.bus.dao.BusRepository;
import com.SmartBus.domain.bus.domain.Bus;
import com.SmartBus.domain.member.dao.MemberRepository;
import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.member.domain.MemberRole;
import com.SmartBus.domain.router.dao.RouterRepository;
import com.SmartBus.domain.router.domain.Router;
import com.SmartBus.domain.schedule.domain.Schedule;
import com.SmartBus.domain.schedule.dao.ScheduleRepository;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleCreateResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleDetailResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleFindAllResponse;
import com.SmartBus.domain.schedule.dto.reponse.ScheduleUpdateResponse;
import com.SmartBus.domain.schedule.dto.request.ScheduleCreateRequest;
import com.SmartBus.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.SmartBus.global.error.exception.CustomException;
import com.SmartBus.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final BusRepository busRepository;
    private final MemberRepository memberRepository;
    private final RouterRepository routerRepository;
    public ScheduleCreateResponse createSchedule(ScheduleCreateRequest scheduleCreateRequest) {
        Bus bus = busRepository.findById(scheduleCreateRequest.busId())
                .orElseThrow(() -> new CustomException(ErrorCode.BUS_NOT_FOUND));

        Member driver = memberRepository.findById(scheduleCreateRequest.driverId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (driver.getRole() != MemberRole.DRIVER) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_ROLE);
        }

        Router router = routerRepository.findById(scheduleCreateRequest.routerId())
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTER_NOT_FOUND));

        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(
                bus,
                driver,
                router,
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
    public ScheduleDetailResponse getScheduleById(Long id) {
        Schedule schedule =  scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        return ScheduleDetailResponse.from(schedule);
    }

   public ScheduleUpdateResponse updateSchedule(Long id, ScheduleUpdateRequest updatedSchedule) {
       Schedule schedule = scheduleRepository.findById(id)
               .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

       Bus bus = busRepository.findById(updatedSchedule.busId())
               .orElseThrow(() -> new CustomException(ErrorCode.BUS_NOT_FOUND));

       Member driver = memberRepository.findById(updatedSchedule.driverId())
               .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

       if (driver.getRole() != MemberRole.DRIVER) {
           throw new CustomException(ErrorCode.INVALID_MEMBER_ROLE);
       }

       Router router = routerRepository.findById(updatedSchedule.routerId())
               .orElseThrow(() -> new CustomException(ErrorCode.ROUTER_NOT_FOUND));

       schedule.update(
               bus,
               driver,
               router,
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