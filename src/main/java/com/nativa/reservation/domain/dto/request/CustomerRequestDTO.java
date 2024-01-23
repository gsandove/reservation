package com.nativa.reservation.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRequestDTO {
    private String name;
    private String lastName;
    private String documentNumber;
}
