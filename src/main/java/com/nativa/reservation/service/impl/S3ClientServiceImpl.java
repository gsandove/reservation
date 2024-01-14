package com.nativa.reservation.service.impl;

import com.nativa.reservation.service.S3ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Slf4j
@Component
public class S3ClientServiceImpl implements S3ClientService {

//    @Value("app.s3.bucketName")
    private final String BUCKET_NAME = "reservation.app";

    private final S3Client s3Client;

    public S3ClientServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }


    @Override
    public Object get(String keyName) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyName)
                .build();
        log.info(String.format("Get from bucket: %s, key: %s.",BUCKET_NAME,keyName));
        GetObjectResponse response = this.s3Client.getObject(request).response();
        log.info(String.format("Response content: %s",response.contentType()));
        return response;
    }

    @Override
    public Boolean upload(Object object) {
        return null;
    }

    @Override
    public Boolean delete(String keyName) {
        return null;
    }
}
