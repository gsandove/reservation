package com.nativa.reservation.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationRequestDTO {
    private Date reservationDay;
    @JsonFormat(pattern = "h:mm:ss")
    private String startHour;
    @JsonFormat(pattern = "hh:mm:ss:nn")
    private String endHour;
    private BigDecimal payAmount;
    private UUID stadiumId;
    private UUID customerId;
}
