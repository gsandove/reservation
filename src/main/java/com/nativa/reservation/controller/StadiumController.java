package com.nativa.reservation.controller;

import com.nativa.reservation.domain.dto.request.StadiumRequestDTO;
import com.nativa.reservation.domain.dto.response.StadiumResponseDTO;
import com.nativa.reservation.service.StadiumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stadiums")
public class StadiumController {

    private final StadiumService service;

    public StadiumController(StadiumService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<StadiumResponseDTO> save(@RequestBody StadiumRequestDTO requestDTO){
        StadiumResponseDTO response = this.service.save(requestDTO);
        return  ResponseEntity
                .status( response == null ? HttpStatus.INTERNAL_SERVER_ERROR.value() : HttpStatus.OK.value())
                .body(response);
    }
}
