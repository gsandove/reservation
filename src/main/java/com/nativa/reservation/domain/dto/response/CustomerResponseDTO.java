package com.nativa.reservation.domain.dto.response;

import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO extends CustomerRequestDTO {
    private UUID uuid;
    private Integer counter;
}
