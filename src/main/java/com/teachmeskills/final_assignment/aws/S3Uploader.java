package com.teachmeskills.final_assignment.aws;


import com.teachmeskills.final_assignment.exception.WrongFileException;
import com.teachmeskills.final_assignment.properties.PropertiesManager;
import com.teachmeskills.final_assignment.utils.Constants;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.nio.file.Paths;

public class S3Uploader {

    public static void s3() throws WrongFileException {
        String accessKey = PropertiesManager.loadProperties().getProperty("access.key");
        String secretKey = PropertiesManager.loadProperties().getProperty("secret.key");
        String bucketName = PropertiesManager.loadProperties().getProperty("bucketName");
        String regionName = PropertiesManager.loadProperties().getProperty("eu.north.1");

        // TODO в кавычки вставить название файла с расширением
        String key = Constants.KEY_S3_NAME_FILE_REPORT;
        // TODO полный путь к файлу
        File file = new File(Constants.PATH_TO_REPORT_FILE);

       AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

       S3Client s3Client = S3Client
                .builder()
                .region(Region.of(regionName))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectResponse response = s3Client.putObject(request, Paths.get(file.toURI())  );

        System.out.println(Constants.STATUS_SUCCESS_UPLOAD_TO_AWS + response.eTag());
    }
}