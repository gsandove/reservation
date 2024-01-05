package com.nativa.reservation.service;

import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.domain.dto.request.CustomerUpdateRequestDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponseDTO> findAll();

    CustomerResponseDTO findById(UUID uuid);

    CustomerResponseDTO save(CustomerRequestDTO requestDTO);

    CustomerResponseDTO update(CustomerUpdateRequestDTO updateRequestDTO);

    void delete(UUID uuid);
}
