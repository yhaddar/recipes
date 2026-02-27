package com.recipes.recipe.request;

import com.recipes.recipe.annotation.ImageTypeAnnotation;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryRequest {
    @NotBlank(message = "category title is required")
    @Size(max = 20, message = "category title cannot exceed 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9\\s-_]+$", message = "category title contains invalid characters or starts with a space.")
    private String title;

    @ImageTypeAnnotation
    private MultipartFile image;

    public boolean isImageNull(){
        return this.image == null;
    }
}
