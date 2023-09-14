package com.hecheng.minio.service;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class MinioBucketService {

    @Autowired
    private MinioClient minioClient;

    /**
     * 创建一个新的 bucket。
     *
     * @param bucketName 要创建的 bucket 的名称
     */
    public void createBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error creating bucket in Minio", e);
        }
    }

    /**
     * 获取所有的 buckets。
     *
     * @return 返回一个包含所有 buckets 的列表
     */
    public List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error listing buckets from Minio", e);
        }
    }

    /**
     * 删除一个 bucket。
     *
     * @param bucketName 要删除的 bucket 的名称
     */
    public void removeBucket(String bucketName) {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error removing bucket from Minio", e);
        }
    }
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        }  catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error checking existing from Minio", e);
        }
    }
}

