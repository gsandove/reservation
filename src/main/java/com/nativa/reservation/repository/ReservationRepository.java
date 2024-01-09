package com.nativa.reservation.repository;

import com.nativa.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAllByDeletedAtIsNull();
    Optional<Reservation> findByUuidAndDeletedAtIsNull(UUID uuid);

    @Query("SELECT r FROM Reservation r WHERE (r.reservationDay BETWEEN :startHourDay AND :endHourDay) AND r.deletedAt IS NULL")
    List<Reservation> findAllByDeletedAtIsNullAndReservationDayIsBetween(@Param("startHourDay")LocalDateTime startHourDay,@Param("endHourDay")LocalDateTime endHourDay);

}
