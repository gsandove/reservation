package com.nativa.reservation.jobs;

import com.nativa.reservation.domain.dto.response.ReservationResponseDTO;
import com.nativa.reservation.service.ReservationService;
import com.nativa.reservation.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

//@Component
//@Slf4j
public class AlarmJob{

    //TODO: implementa un getAlarm con quartz, debes usar un cronJob por que este resiste para más ejecuciones durante el día

//    private final ReservationService reservationService;
//
//    public AlarmJob(ReservationService reservationService) {
//        this.reservationService = reservationService;
//    }
//
//    @Scheduled(cron = "0 * * * * *")
//    public void getAlarm(){
//        //TODO: obtener las reservación de la siguiente hora
//        Integer nextHourNumber = LocalTime.now().getHour() + 1;
//        LocalTime nextHour = LocalTime.parse(String.format("%s:00:00", TimeUtil.formatHour(nextHourNumber)));
//
//        ReservationResponseDTO nextRevervationHour = this.reservationService.findReservationByStartHour(nextHour);
//
//        log.info("Next Reservation : {}",nextRevervationHour.toString());
//    }
}
