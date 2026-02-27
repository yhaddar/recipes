package com.recipes.recipe.dto;

import com.recipes.recipe.enums.Difficulty;
import com.recipes.recipe.enums.Type;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class RecipeDTO {
    private String recipeTitle;
    private String description;
    private double cookingTime;
    private Category category;
    private User user;
    private Type type;
    private String mediaUrl;
    private Difficulty difficulty;
    private String countryOrigin;
}
