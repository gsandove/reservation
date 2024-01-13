package com.nativa.reservation.service.impl;

import com.nativa.reservation.domain.Customer;
import com.nativa.reservation.domain.Reservation;
import com.nativa.reservation.domain.Stadium;
import com.nativa.reservation.domain.dto.request.ReservationRequestDTO;
import com.nativa.reservation.domain.dto.request.ReservationUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.ReservationResponseDTO;
import com.nativa.reservation.repository.ReservationRepository;
import com.nativa.reservation.service.ReservationService;
import io.awspring.cloud.s3.S3Template;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final S3Template s3Template;
    private final String BUCKET_NAME = "reservation.app"; //luego pasara a ser una variable en app.properties

    public ReservationServiceImpl(ReservationRepository repository, S3Template s3Template) {
        this.repository = repository;
        this.s3Template = s3Template;
    }

    @Override
    public ReservationResponseDTO save(ReservationRequestDTO requestDTO) {
        //find Stadium
        Reservation reservation = Reservation.builder()
                .createdAt(LocalDateTime.now())
                .reservationDay(requestDTO.getReservationDay())
                .startHour(LocalTime.parse(requestDTO.getStartHour()))
                .endHour(LocalTime.parse(requestDTO.getEndHour()))
                .payAmount(requestDTO.getPayAmount())
                .customer(Customer.builder()
                        .uuid(requestDTO.getCustomerId())
                        .build())
                .stadium(Stadium.builder()
                        .uuid(requestDTO.getStadiumId())
                        .build())
                .build();
        //find Customer
        Reservation entitySaved = this.repository.save(reservation);
        return this.toDto(entitySaved);
    }

    @Override
    public ReservationResponseDTO udpate(ReservationUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<ReservationResponseDTO> findNowDayReservations() {
        this.getVoucherFile();
        return this.repository
                .findAllByDeletedAtIsNullAndReservationDayIsBetween(
                        LocalDateTime.now().with(LocalTime.MIN),
                        LocalDateTime.now().with(LocalTime.MAX))
                .stream()
                .map(this::toDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Reservation> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);

        if(!entityOpt.isPresent())
            return;

        Reservation entity = entityOpt.get();
        entity.setDeletedAt(LocalDateTime.now());

        this.repository.save(entity);
    }

    @Override
    public void saveVoucher() {

    }

    @Override
    public void getVoucherFile() {

        File file = s3Template.createSignedGetURL(BUCKET_NAME, "CARNET-COVID.jpeg", Duration.ofMinutes(3));

    }

    private ReservationResponseDTO toDto(Reservation reservation){
        if(reservation == null)
            return null;
        return ReservationResponseDTO.builder()
                .uuid(reservation.getUuid())
                .reservationDay(reservation.getReservationDay())
                .startHour(reservation.getStartHour().toString())
                .endHour(reservation.getEndHour().toString())
                .payAmount(reservation.getPayAmount())
                .customerId(reservation.getCustomer().getUuid())
                .stadiumId(reservation.getStadium().getUuid())
                .build();
    }
}
