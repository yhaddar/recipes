package com.recipes.recipe.dto;

import com.recipes.recipe.models.Category;
import com.recipes.recipe.models.Recipe;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
    private UUID id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String title;
    private String image;
    private List<Recipe> recipes;

    public static CategoryDTO toJSON(Category category){
        return CategoryDTO.builder()
                .title(category.getTitle())
                .image(category.getImage())
                .id(category.getId())
                .build();
    }
}
