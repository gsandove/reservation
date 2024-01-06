package com.nativa.reservation.service.impl;

import com.nativa.reservation.domain.Stadium;
import com.nativa.reservation.domain.dto.request.StadiumRequestDTO;
import com.nativa.reservation.domain.dto.request.StadiumUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.StadiumResponseDTO;
import com.nativa.reservation.repository.StadiumRepository;
import com.nativa.reservation.service.StadiumService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository repository;

    public StadiumServiceImpl(StadiumRepository stadiumRepository) {
        this.repository = stadiumRepository;
    }

    @Override
    public List<StadiumResponseDTO> findAll() {
        return this.repository.findAllByDeletedAtIsNull()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StadiumResponseDTO findById(UUID uuid) {
        Optional<Stadium> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);
        if(entityOpt.isPresent() == false)
            return null;;
        return this.toDto(entityOpt.get());
    }

    @Override
    public StadiumResponseDTO save(StadiumRequestDTO requestDTO) {
        Stadium stadium = Stadium.builder()
                .name(requestDTO.getName())
                .address(requestDTO.getAddress())
                .price(requestDTO.getPrice())
                .createdAt(new Date())
                .build();
        Stadium saveEntity = this.repository.save(stadium);

        return this.toDto(saveEntity);
    }

    @Override
    public StadiumResponseDTO update(StadiumUpdateRequestDTO updateRequestDTO) {
        Optional<Stadium> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(updateRequestDTO.getUuid());
        if(entityOpt.isPresent() == false)
            return null;

        Stadium entity = entityOpt.get();
        entity.setName(updateRequestDTO.getName());
        entity.setAddress(updateRequestDTO.getAddress());
        entity.setPrice(updateRequestDTO.getPrice());
        this.repository.save(entity);

        return this.toDto(entity);
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Stadium> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);
        if(entityOpt.isPresent() == false)
            return;

        Stadium entity = entityOpt.get();
        entity.setDeletedAt(new Date());
        this.repository.save(entity);
    }

    private StadiumResponseDTO toDto(Stadium entity){
        return StadiumResponseDTO.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .address(entity.getAddress())
                .price(entity.getPrice())
                .build();
    }
}
