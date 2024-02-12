package com.nativa.reservation.quartz.reservations;

import com.nativa.reservation.domain.dto.response.ReservationResponseDTO;
import com.nativa.reservation.service.ReservationService;
import com.nativa.reservation.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Slf4j
public class NextReservationAlarmJob implements Job {

    private final ReservationService reservationService;

    public NextReservationAlarmJob(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      log.info("Start Next Reservation Job");
      LocalTime nextHourTime = LocalTime.parse( (TimeUtil.formatHour(LocalTime.now().getHour() + 1)+":00:00" ));
      ReservationResponseDTO response = this.reservationService.findReservationInNowDayByStartHour(nextHourTime);
      if(response != null)
        log.info("Existe una siguiente reservacion.");
    }
}
