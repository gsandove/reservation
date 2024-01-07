package com.nativa.reservation.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationRequestDTO {
    private Date reservationDay;
    private LocalTime startHour;
    private LocalTime endHour;
    private BigDecimal payAmount;
    private UUID stadiumId;
    private UUID customerId;
}
