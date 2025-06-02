package com.recipes.recipe.Service.Impl;

import com.recipes.recipe.Service.UploadToS3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Date;

@Service
public class S3Impl implements S3Service {

    private final S3Client s3Client;

    @Autowired
    public S3Impl(S3Client s3Client){
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String folder) {

        try{

            Date now = new Date();
            String file_name = folder + "/" + now.getTime()+ "_" +multipartFile.getOriginalFilename();
            String bucket = "recipesyhaddar";
            
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(file_name)
                .contentType(multipartFile.getContentType()) 
                .build();

            this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));

            return String.format("https://recipesyhaddar.s3.us-east-1.amazonaws.com/%s", file_name);
        }catch(Exception e){
            return "failed to upload image, " + e.getMessage();
        }
    }
}