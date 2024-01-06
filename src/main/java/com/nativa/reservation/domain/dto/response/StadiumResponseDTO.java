package com.nativa.reservation.domain.dto.response;

import com.nativa.reservation.domain.dto.request.StadiumRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StadiumResponseDTO extends StadiumRequestDTO {
    private UUID uuid;
}
