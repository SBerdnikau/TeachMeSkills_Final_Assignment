package com.teachmeskills.final_assignment.aws;

import com.teachmeskills.final_assignment.exception.InvalidFileException;
import com.teachmeskills.final_assignment.log.Logger;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class for uploading data to AWS S3 server
 */
public class S3Uploader {

    public static void s3() throws InvalidFileException {
        String accessKey = PropertiesManager.loadProperties().getProperty("access.key");
        String secretKey = PropertiesManager.loadProperties().getProperty("secret.key");
        String bucketName = PropertiesManager.loadProperties().getProperty("bucketName");
        String regionName = PropertiesManager.loadProperties().getProperty("regionName");

        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

        String key = date + Constants.KEY_S3_NAME_FILE_REPORT;
        File file = new File(Constants.PATH_TO_REPORT_FILE);

        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        PutObjectResponse response;
        try (S3Client s3Client = S3Client
                .builder()
                .region(Region.of(regionName))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build()) {

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            response = s3Client.putObject(request, Paths.get(file.toURI()));
            Logger.logInfo(Constants.MESSAGE_SUCCESS_UPLOAD_TO_AWS + response.eTag());
        } catch (Exception e) {
            Logger.logException(e);
            System.out.println(Constants.MESSAGE_EXCEPTION_UPLOAD_AWS + e.getMessage() + Constants.MESSAGE_CODE_ERROR + Constants.ERROR_CODE_UPLOAD);
        }
    }
}