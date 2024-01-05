package com.nativa.reservation.repository;

import com.nativa.reservation.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StadiumRepository extends JpaRepository<Stadium, UUID> {
}
