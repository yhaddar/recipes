package com.recipes.recipe.DTO.RecipeDTO;

import com.recipes.recipe.DTO.BaseDTO;
import com.recipes.recipe.Model.Entity.Recipe;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RecipeShowDTOResponse extends BaseDTO {
    private String title;
    private String image;
    private Integer prep_time;
    private Integer cook_time;
    private String description;
    private String category_title;

    public static RecipeShowDTOResponse EntityToJson(Recipe recipe){
        return RecipeShowDTOResponse.builder()
            .id(recipe.getId())
            .title(recipe.getTitle())
            .image("https://recipesyhaddar.s3.us-east-1.amazonaws.com/" + recipe.getImage())
            .prep_time(recipe.getPrep_time())
            .cook_time(recipe.getCook_time())
            .category_title(recipe.getCategory().getTitle())
            .description(recipe.getDescription())
            .created_at(recipe.getCreated_at())
            .updated_at(recipe.getUpdated_at())
            .build();
    }
}