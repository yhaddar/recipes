package com.recipes.recipe.Service.Impl;

import com.recipes.recipe.Service.UploadToS3.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Date;

@Service
public class S3Impl implements S3Service {

    private final S3Client s3Client;
    private final String bucket = "recipesyhaddar";
    private final Logger log = LoggerFactory.getLogger(S3Impl.class);

    @Autowired
    public S3Impl(S3Client s3Client){
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String folder) {

        try{

            Date now = new Date();
            String file_name = folder + "/" + now.getTime()+ "_" +multipartFile.getOriginalFilename();
            
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(this.bucket)
                .key(file_name)
                .contentType(multipartFile.getContentType()) 
                .build();

            this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));

            return file_name;
        }catch(Exception e){
            return "failed to upload image, " + e.getMessage();
        }
    }

    @Override
    public void deleteFile(String file) {
       try {
           DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .key(file)
                    .bucket(this.bucket)
                    .build();

            this.s3Client.deleteObject(deleteObjectRequest);

       }catch (Exception e){
           log.error("faile to remove the image : {}", e.getMessage());
       }
    }
}