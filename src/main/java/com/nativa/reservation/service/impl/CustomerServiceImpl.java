package com.nativa.reservation.service.impl;

import com.nativa.reservation.domain.Customer;
import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import com.nativa.reservation.domain.dto.request.CustomerUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.repository.CustomerRepository;
import com.nativa.reservation.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    @Override
    public List<CustomerResponseDTO> findAll() {
        return this.repository.findAllByDeletedAtIsNull()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO findById(UUID uuid) {
        Optional<Customer> entityOpt = this.repository.findById(uuid);
        if(entityOpt.isPresent() == false)
            return null;
        return this.toDto(entityOpt.get());
    }

    @Override
    public CustomerResponseDTO save(CustomerRequestDTO requestDTO) {
        Customer customer =  Customer.builder()
                .name(requestDTO.getName())
                .lastname(requestDTO.getLastname())
                .documentNumber(requestDTO.getDocumentNumber())
                .counter(0)
                .build();

        Customer saveEntity = this.repository.save(customer);

        return this.toDto(saveEntity);
    }

    @Override
    public CustomerResponseDTO update(CustomerUpdateRequestDTO updateRequestDTO) {
        Optional<Customer> entityOpt = this.repository.findById(updateRequestDTO.getUuid());

        if(entityOpt.isPresent() == false)
            return null;

        Customer entity = entityOpt.get();
        entity.setName(updateRequestDTO.getName());
        entity.setLastname(updateRequestDTO.getLastname());
        entity.setDocumentNumber(updateRequestDTO.getDocumentNumber());

        this.repository.save(entity);

        return this.toDto(entity);
    }

    @Override
    public void delete(UUID uuid) {

    }

    private CustomerResponseDTO toDto(Customer customer){
        return CustomerResponseDTO.builder()
                .uuid(customer.getUuid())
                .name(customer.getName())
                .lastname(customer.getLastname())
                .documentNumber(customer.getDocumentNumber())
                .build();
    }


}
