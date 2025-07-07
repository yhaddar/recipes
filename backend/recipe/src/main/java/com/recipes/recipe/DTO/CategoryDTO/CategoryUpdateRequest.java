package com.recipes.recipe.DTO.CategoryDTO;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryUpdateRequest {
    private MultipartFile image;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,20}$", message = "this title is invalid")
    private String title;
}