package com.nativa.reservation.repository;

import com.nativa.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAllByDeletedAtIsNull();
    Optional<Reservation> findByUuidAndDeletedAtIsNull(UUID uuid);
}
