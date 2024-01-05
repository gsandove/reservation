package com.nativa.reservation.domain.dto.response;

import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO extends CustomerRequestDTO {
    private UUID uuid;
    private Integer counter;
}
