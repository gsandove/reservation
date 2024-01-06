package com.nativa.reservation.service;

import com.nativa.reservation.domain.dto.request.StadiumRequestDTO;
import com.nativa.reservation.domain.dto.request.StadiumUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.StadiumResponseDTO;

import java.util.List;
import java.util.UUID;

public interface StadiumService {

    List<StadiumResponseDTO> findAll();
    StadiumResponseDTO findById(UUID uuid);
    StadiumResponseDTO save(StadiumRequestDTO requestDTO);
    StadiumResponseDTO update(StadiumUpdateRequestDTO updateRequestDTO);
    void delete(UUID uuid);

}
