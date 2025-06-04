package com.recipes.recipe.DTO.RecipeDTO;

import com.recipes.recipe.Model.Entity.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RecipeDTOResponse {
    private UUID id;
    private String title;
    private String image;
    private Integer prep_time;
    private String category_title;

    public static RecipeDTOResponse EntityToJson(Recipe recipe){
        return RecipeDTOResponse.builder()
            .id(recipe.getId())
            .title(recipe.getTitle())
            .image(recipe.getImage())
            .prep_time(recipe.getPrep_time())
            .category_title(recipe.getCategory().getTitle())
            .build();
    }
}