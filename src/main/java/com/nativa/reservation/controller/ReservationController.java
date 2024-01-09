package com.nativa.reservation.controller;

import com.nativa.reservation.Error.ErrorMessages;
import com.nativa.reservation.domain.dto.request.ReservationRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.domain.dto.response.ReservationResponseDTO;
import com.nativa.reservation.domain.dto.response.StadiumResponseDTO;
import com.nativa.reservation.service.CustomerService;
import com.nativa.reservation.service.ReservationService;
import com.nativa.reservation.service.StadiumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final StadiumService stadiumService;
    private final CustomerService customerService;

    public ReservationController(ReservationService reservationService, StadiumService stadiumService, CustomerService customerService) {
        this.reservationService = reservationService;
        this.stadiumService = stadiumService;
        this.customerService = customerService;
    }

    @GetMapping("/dayNow")
    ResponseEntity<List<ReservationResponseDTO>> findAllReservationsNowDay(){
        List<ReservationResponseDTO> response = this.reservationService.findNowDayReservations();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<?> save(@RequestBody ReservationRequestDTO requestDTO) {
        //buscar en service Stadium si existe sino retorna error que no existe
        StadiumResponseDTO stadiumRequest = this.stadiumService.findById(requestDTO.getStadiumId());
        if(stadiumRequest == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Error : "+ ErrorMessages.ERROR_STADIUM_NO_EXISTS);
        //buscar en service Customer si existe sino retonar error que no existe
        CustomerResponseDTO customerRequest = this.customerService.findById(requestDTO.getCustomerId());
        if (customerRequest == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Error : "+ ErrorMessages.ERROR_CUSTOMER_NO_EXISTS);

        ReservationResponseDTO response = this.reservationService.save(requestDTO);
        if(response == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("Error : "+ ErrorMessages.ERROR_RESERVATION_NO_EXISTS);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    ResponseEntity<?> delete(@RequestParam UUID uuid){
        try{
            this.reservationService.delete(uuid);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : "+e.getMessage());
        }
    }
}
