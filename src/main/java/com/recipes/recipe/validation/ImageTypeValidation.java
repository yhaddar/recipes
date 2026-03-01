package com.recipes.recipe.validation;

import com.recipes.recipe.annotation.ImageTypeAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public class ImageTypeValidation implements ConstraintValidator<ImageTypeAnnotation, MultipartFile> {

    @Override
    public void initialize(ImageTypeAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile.isEmpty())
            return false;
        List<String> extensions = List.of("jpg", "jpeg", "png", "mp4");
        return extensions.contains(Objects.requireNonNull(multipartFile.getContentType()).split("/")[1]);
    }
}
