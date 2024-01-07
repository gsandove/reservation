package com.nativa.reservation.controller;

import com.nativa.reservation.Error.ErrorMessages;
import com.nativa.reservation.domain.dto.request.StadiumRequestDTO;
import com.nativa.reservation.domain.dto.request.StadiumUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.StadiumResponseDTO;
import com.nativa.reservation.service.StadiumService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<StadiumResponseDTO>> findAll(){
        List<StadiumResponseDTO> responseDTOS = this.service.findAll();
        if(responseDTOS.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findById(@PathVariable("uuid") UUID uuid) throws BadRequestException {
        StadiumResponseDTO responseDTO = this.service.findById(uuid);
        if (responseDTO == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error : " + ErrorMessages.ERROR_STADIUM_NO_EXISTS);
        return ResponseEntity.ok(this.service.findById(uuid));
    }

    @PutMapping
    public ResponseEntity<?> update(StadiumUpdateRequestDTO requestDTO) throws BadRequestException {
        StadiumResponseDTO response = this.service.update(requestDTO);
        if(response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error : " + ErrorMessages.ERROR_STADIUM_NO_EXISTS);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam UUID uuid){
        try{
            this.service.delete(uuid);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : "+e.getMessage());
        }
    }
}
