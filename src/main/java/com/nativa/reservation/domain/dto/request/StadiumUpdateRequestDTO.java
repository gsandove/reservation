package com.nativa.reservation.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StadiumUpdateRequestDTO extends StadiumRequestDTO{
    private UUID uuid;
}
