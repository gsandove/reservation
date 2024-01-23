package com.nativa.reservation.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequestDTO extends CustomerRequestDTO{
    private UUID uuid;
}
