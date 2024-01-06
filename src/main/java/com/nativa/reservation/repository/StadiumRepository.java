package com.nativa.reservation.repository;

import com.nativa.reservation.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StadiumRepository extends JpaRepository<Stadium, UUID> {

    List<Stadium> findAllByDeletedAtIsNull();
    Optional<Stadium> findByUuidAndDeletedAtIsNull(UUID uuid);
}
