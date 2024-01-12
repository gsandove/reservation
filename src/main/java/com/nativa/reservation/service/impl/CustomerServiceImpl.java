package com.nativa.reservation.service.impl;

import com.nativa.reservation.Error.ErrorMessages;
import com.nativa.reservation.domain.Customer;
import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import com.nativa.reservation.domain.dto.request.CustomerUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.repository.CustomerRepository;
import com.nativa.reservation.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final S3Client s3Client;

    public CustomerServiceImpl(CustomerRepository customerRepository, S3Client s3Client) {
        this.repository = customerRepository;
        this.s3Client = s3Client;
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
        Optional<Customer> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);
        if(entityOpt.isPresent() == false)
            return null;;
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
        Optional<Customer> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(updateRequestDTO.getUuid());

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
        Optional<Customer> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);
        if(entityOpt.isPresent() == false)
            return;
        Customer entity = entityOpt.get();
        entity.setDeletedAt(new Date());
        this.repository.save(entity);
    }

    @Override
    public void saveDocument() {

    }

    @Override
    public void getDocumentFile() {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("reservation.app")
                .key("CARNET-COVID.jpeg")
                .build();
        GetObjectResponse response = s3Client.getObject(request).response();
        log.info("imagen: {}", response.contentType());
    }

    private CustomerResponseDTO toDto(Customer customer){
        if(customer == null)
            return null;
        return CustomerResponseDTO.builder()
                .uuid(customer.getUuid())
                .name(customer.getName())
                .lastname(customer.getLastname())
                .documentNumber(customer.getDocumentNumber())
                .build();
    }


}
