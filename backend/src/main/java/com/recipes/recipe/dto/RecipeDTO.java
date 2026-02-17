package com.recipes.recipe.dto;

import com.recipes.recipe.enums.Difficulty;
import com.recipes.recipe.enums.Type;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class RecipeDTO {
    private String recipe_title;
    private String description;
    private double cooking_time;
    private Category category;
    private User user;
    private Type type;
    private String media_url;
    private Difficulty difficulty;
    private String country_origin;
}
