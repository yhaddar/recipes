package com.recipes.recipe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.recipes.recipe.enums.Difficulty;
import com.recipes.recipe.enums.Type;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.models.Recipe;
import com.recipes.recipe.models.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecipeDTO {
    private UUID id;
    private String recipeTitle;
    private String description;
    private double cookingTime;
    private CategoryDTO category;
    private UserRecipeDTO user;
    private Type type;
    private String mediaUrl;
    private Difficulty difficulty;
    private String countryOrigin;
    @JsonFormat(pattern = "MMM dd, yyyy")
    private LocalDateTime createdAt;

    public static RecipeDTO toJSON(Recipe recipe){
        return RecipeDTO.builder()
                .id(recipe.getId())
                .recipeTitle(recipe.getRecipe_title())
                .description(recipe.getDescription())
                .cookingTime(recipe.getCooking_time())
                .category(CategoryDTO.toJSON(recipe.getCategory()))
                .user(UserRecipeDTO.toJSON(recipe.getUser()))
                .type(recipe.getType())
                .mediaUrl(recipe.getMedia_url())
                .difficulty(recipe.getDifficulty())
                .countryOrigin(recipe.getCountry_origin())
                .createdAt(recipe.getCreated_at())
                .build();
    }

}
