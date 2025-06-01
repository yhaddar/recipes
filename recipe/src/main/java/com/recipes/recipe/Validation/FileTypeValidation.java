package com.recipes.recipe.Validation;

import com.recipes.recipe.Annotation.FileType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public class FileTypeValidation implements ConstraintValidator<FileType, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        if(multipartFile.isEmpty())
            return false;

        List<String> extension = List.of("image/png", "image/jpg", "image/jpeg", "image/webp");
        return extension.contains(multipartFile.getContentType());
    }
}