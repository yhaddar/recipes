package com.recipes.recipe.Service.UploadToS3;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile multipartFile, String folder);
}