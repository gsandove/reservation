package com.nativa.reservation.service;

import com.nativa.reservation.domain.dto.request.ReservationRequestDTO;
import com.nativa.reservation.domain.dto.request.ReservationUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.ReservationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    // crear reservación
    ReservationResponseDTO save(ReservationRequestDTO requestDTO);
    // actualizar reservación
    ReservationResponseDTO udpate(ReservationUpdateRequestDTO requestDTO);
    // buscar reservaciones del día
    List<ReservationResponseDTO> findNowDayReservations();
    // buscar reservaciones entre días
    // buscar reservación por id
    // buscar reservaciones de la semana
    // buscar reservaciones del día por stadio
    // buscar reservaciones por cliente
    // buscar reservaciones del día por cliente
    // buscar reservación por día y cliente
    // eliminar reservación por id

    void delete(UUID uuid);
}
