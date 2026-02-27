package com.recipes.recipe.config;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class S3Config {
    private final S3Template s3Template;
    private static final String BUCKET = "recipesyhaddar";

    public S3Config(S3Template s3Template){
        this.s3Template = s3Template;
    }

    public void uploadFile(String key, InputStream inputStream){
        this.s3Template.upload(BUCKET, key, inputStream);
    }

    public void deleteFile(String key){
        this.s3Template.deleteObject(BUCKET, key.split(".com/")[1]);
    }

    @Transactional
    public void updateFile(String key, InputStream inputStream, String lastKey){
        this.uploadFile(key, inputStream);
        this.deleteFile(lastKey);
    }

    public S3Resource download(String key){
        return this.s3Template.download(BUCKET, key);
    }

    public String getUrl(String key){
        return "https://recipesyhaddar.s3.us-east-1.amazonaws.com/"+key;
    }
}
