package com.recipes.recipe.DTO.BlogDTO;

import jakarta.validation.Valid;
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
public class BlogDTOUpdateRequest {
    @Pattern(regexp = "^[a-zA-Z-_&\\s]{8,20}$", message = "this title invalide")
    private String title;

    private MultipartFile image;

    @Pattern(regexp = "^[a-zA-Z0-9.,!?()'\"\\s:&_-]{20,255}$", message = "this description was invalide")
    private String description;
}