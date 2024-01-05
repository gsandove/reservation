package com.nativa.reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stadium")
public class Stadium {

    @Id
    private UUID uuid;
    private String name;
    private String address;
    private BigDecimal price;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;


    @OneToMany(mappedBy = "stadium")
    private List<Reservation> reservations;
}
