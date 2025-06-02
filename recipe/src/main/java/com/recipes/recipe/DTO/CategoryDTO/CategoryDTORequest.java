package com.recipes.recipe.DTO.CategoryDTO;

import com.recipes.recipe.Annotation.FileType;
import com.recipes.recipe.Annotation.Unique;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTORequest {
    @Valid
    @NotNull(message = "the image was not be empty")
    @FileType(message = "the type of image invalide, import image with png or jpg or jpeg or webp")
    private MultipartFile image;

    @NotEmpty(message = "the title was not be empty")
    @Unique(message = "this title already exist")
    @Size(min = 3, max = 20, message = "this title must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,20}$", message = "this title is invalide")
    private String title;

}