package com.nativa.reservation.service.impl;

import com.nativa.reservation.domain.Customer;
import com.nativa.reservation.domain.dto.request.CustomerRequestDTO;
import com.nativa.reservation.domain.dto.request.CustomerUpdateRequestDTO;
import com.nativa.reservation.domain.dto.response.CustomerResponseDTO;
import com.nativa.reservation.repository.CustomerRepository;
import com.nativa.reservation.service.CustomerService;
import com.nativa.reservation.service.S3ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final S3ClientService s3ClientService;

    public CustomerServiceImpl(CustomerRepository customerRepository, S3ClientService s3ClientService) {
        this.repository = customerRepository;
        this.s3ClientService = s3ClientService;
    }

    @Override
    public List<CustomerResponseDTO> findAll() {
//        this.getDocumentFile();
        return this.repository.findAllByDeletedAtIsNull()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CustomerResponseDTO findById(UUID uuid) {
//        this.s3ClientService.get("CARNET-COVID.jpeg");
        Optional<Customer> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);
        if(!entityOpt.isPresent())
            return null;
        return this.toDto(entityOpt.get());
    }

    @Override
    public CustomerResponseDTO save(CustomerRequestDTO requestDTO) {
        Optional<Customer> customerOptional = this.repository.findByDocumentNumberAndDeletedAtIsNull(requestDTO.getDocumentNumber());
        if (customerOptional.isPresent())
            return this.toDto(customerOptional.get());
        else {
            Customer customer =  Customer.builder()
                    .name(requestDTO.getName())
                    .lastName(requestDTO.getLastName())
                    .documentNumber(requestDTO.getDocumentNumber())
                    .counter(0)
                    .build();

            Customer saveEntity = this.repository.save(customer);
            return this.toDto(saveEntity);
        }
    }

    @Override
    public CustomerResponseDTO update(UUID uuid, CustomerUpdateRequestDTO updateRequestDTO) {
        Optional<Customer> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);

        if(!entityOpt.isPresent())
            return null;

        Customer entity = entityOpt.get();
        entity.setName(updateRequestDTO.getName());
        entity.setLastName(updateRequestDTO.getLastName());

        Customer updateEntity = this.repository.save(entity);
        return this.toDto(updateEntity);
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Customer> entityOpt = this.repository.findByUuidAndDeletedAtIsNull(uuid);
        if (!entityOpt.isPresent())
            return;
        Customer entity = entityOpt.get();
        entity.setDeletedAt(new Date());
        this.repository.save(entity);
    }

    @Override
    public void saveDocument() {
        File file = new File(getClass().getClassLoader().getResource("simpleTextFile").getPath());
        PutObjectRequest requestToUploadFile = PutObjectRequest.builder().build();
        PutObjectResponse response = PutObjectResponse.builder().build();
        log.info("upload : "+file.getName()+" to S3.");
//        s3Client.putObject(PutObjectRequest.builder()
//                .bucket("reservation.app")
//                .key(file.getName())
//                .build(), Paths.get(file.getPath()));
    }

    @Override
    public void getDocumentFile() {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("reservation.app")
                .key("CARNET-COVID.jpeg")
                .build();
//        GetObjectResponse response = s3Client.getObject(request).response();
//        log.info("imagen: {}", response.contentType());
    }

    private CustomerResponseDTO toDto(Customer customer){
        if(customer == null)
            return null;
        return CustomerResponseDTO.builder()
                .uuid(customer.getUuid())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .documentNumber(customer.getDocumentNumber())
                .counter(customer.getCounter())
                .build();
    }


}
