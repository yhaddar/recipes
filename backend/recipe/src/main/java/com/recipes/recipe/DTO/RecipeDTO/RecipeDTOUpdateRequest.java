package com.recipes.recipe.DTO.RecipeDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeDTOUpdateRequest {
    @Pattern(regexp = "^[a-zA-Z-_&\\s]{8,20}$", message = "this title invalid")
    private String title;

    private MultipartFile image;

    @Pattern(regexp = "^[a-zA-Z0-9.,!?()'\"\\s:&_-]{20,255}$", message = "this description was invalid")
    private String description;

    @Min(1)
    private Integer prep_time;

    @Min(1)
    private Integer cook_time;
}