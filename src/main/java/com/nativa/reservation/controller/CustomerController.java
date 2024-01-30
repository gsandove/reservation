package com.nativa.reservation.controller;

import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import com.nativa.reservation.domain.dto.request.CustomerUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @Operation(summary = "guarda un cliente", description = "guarda un cliente")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> save(@RequestBody CustomerRequestDTO requestDTO) {
        try {
            CustomerResponseDTO response = this.service.save(requestDTO);
            return  ResponseEntity.ok(response);
        }
        catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "obtiene la lista de clientes", description = "obtiene la lista de clientes")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        try {
            List<CustomerResponseDTO> responseDTOS = this.service.findAll();
            if(responseDTOS.isEmpty())
                return ResponseEntity.noContent().build();

            return ResponseEntity.ok(responseDTOS);
        }
        catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "obtiene un cliente", description = "obtiene un cliente")
    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable("uuid") UUID uuid) {
        try {
            CustomerResponseDTO responseDTO = this.service.findById(uuid);
            if (responseDTO == null)
                return ResponseEntity.noContent().build();

            return ResponseEntity.ok(responseDTO);
        }
        catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "actualiza un cliente", description = "actualiza un cliente")
    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable("uuid") UUID uuid, @RequestBody CustomerUpdateRequestDTO requestDTO) {
        try {
            CustomerResponseDTO response = this.service.update(uuid, requestDTO);
            if(response == null)
                return ResponseEntity.noContent().build();

            return ResponseEntity.ok(response);
        }
        catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "elimina un cliente", description = "elimina un cliente")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        try {
            this.service.delete(uuid);
            return ResponseEntity.noContent().build();
        }
        catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
