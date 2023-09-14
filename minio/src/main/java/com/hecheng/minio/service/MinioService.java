package com.hecheng.minio.service;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioBucketService minioBucketService;

    // File Operations

    public void uploadFile(InputStream inputStream, String bucketName, String objectName) {
        ensureBucketExists(bucketName);

        try {
            // Create a temporary file, write the input stream to it, then upload
            Path tempFile = Files.createTempFile("minio-temp-", null);
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(tempFile.toString())
                            .build());
            Files.deleteIfExists(tempFile);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to Minio", e);
        }
    }

    public void uploadFile(Path localPath, String bucketName, String objectName) {
        ensureBucketExists(bucketName);

        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(localPath.toString())
                            .build());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error uploading file to Minio", e);
        }
    }

    public void downloadFile(String bucketName, String objectName, OutputStream outputStream) {
        ensureBucketExists(bucketName);

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build())) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from Minio", e);
        }
    }

    public void downloadFile(String bucketName, String objectName, Path localPath) {
        ensureBucketExists(bucketName);

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build())) {

            Files.copy(stream, localPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from Minio", e);
        }
    }

    private void ensureBucketExists(String bucketName) {
        if (!minioBucketService.bucketExists(bucketName)) {
            minioBucketService.createBucket(bucketName);
        }
    }
}
