package com.recipes.recipe.DTO.RecipeDTO;

import com.recipes.recipe.DTO.BaseDTO;
import com.recipes.recipe.DTO.UserDTO.UserDTO;
import com.recipes.recipe.Model.Entity.Recipe;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RecipeDTOResponse extends BaseDTO {
    private String title;
    private String image;
    private Integer prep_time;
    private Integer cook_time;
    private String category_title;
    private String first_name;
    private String last_name;
    private String profile;

    public static RecipeDTOResponse EntityToJson(Recipe recipe) {
        return RecipeDTOResponse.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .image("https://recipesyhaddar.s3.us-east-1.amazonaws.com/" + recipe.getImage())
                .prep_time(recipe.getPrep_time())
                .cook_time(recipe.getCook_time())
                .category_title(recipe.getCategory().getTitle())
                .created_at(recipe.getCreated_at())
                .updated_at(recipe.getUpdated_at())
                .build();
    }
}