package com.nativa.reservation.service;

import java.io.File;

public interface S3ClientService {

    // get -> BUCKET_NAME, File
    Object get(String keyName);
    // upload -> BUCKET_NAME, File
    Boolean upload(Object object);
    // delete -> BUCKET_NAME, path
    Boolean delete(String keyName);
}
