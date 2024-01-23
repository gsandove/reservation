package com.nativa.reservation.repository;

import com.nativa.reservation.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByDocumentNumberAndDeletedAtIsNull(String documentNumber);
    List<Customer> findAllByDeletedAtIsNull();
    Optional<Customer> findByUuidAndDeletedAtIsNull(UUID uuid);
}
