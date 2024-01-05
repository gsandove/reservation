package com.nativa.reservation.controller;

import com.nativa.reservation.Error.ErrorMessages;
import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import com.nativa.reservation.domain.dto.request.CustomerUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.service.CustomerService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> save(@RequestBody CustomerRequestDTO requestDTO){
        CustomerResponseDTO response = this.service.save(requestDTO);
        return  ResponseEntity
                .status( response == null ? HttpStatus.INTERNAL_SERVER_ERROR.value() : HttpStatus.OK.value())
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll(){
        List<CustomerResponseDTO> responseDTOS = this.service.findAll();
        if(responseDTOS.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(responseDTOS);
    }

    @PutMapping
    public ResponseEntity<?> update(CustomerUpdateRequestDTO requestDTO) throws BadRequestException {
        CustomerResponseDTO response = this.service.update(requestDTO);
        if(response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error : " + ErrorMessages.ERROR_CUSTOMER_NO_EXISTS);

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
