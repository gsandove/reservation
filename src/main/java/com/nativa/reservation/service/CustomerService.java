package com.nativa.reservation.service;

import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.domain.dto.request.CustomerUpdateRequestDTO;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponseDTO> findAll();

    CustomerResponseDTO findById(UUID uuid) throws BadRequestException;

    CustomerResponseDTO save(CustomerRequestDTO requestDTO);

    CustomerResponseDTO update(CustomerUpdateRequestDTO updateRequestDTO) throws BadRequestException;

    void delete(UUID uuid) throws BadRequestException;
}
