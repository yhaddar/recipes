package com.recipes.recipe.request;

import com.recipes.recipe.annotation.ImageTypeAnnotation;
import com.recipes.recipe.enums.Difficulty;
import com.recipes.recipe.enums.Type;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecipeRequest {
    @NotBlank(message = "recipe title is required")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9\\s-_]+$", message = "recipe title must contain only letters with spaces and without special characters")
    private String recipeTitle;

    @NotBlank(message = "description is required")
    @Size(max = 500, message = "description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "cooking time is required")
    private double cookingTime;

    @NotNull(message = "category should be not empty")
    private Category category;

    private User user;

    @NotNull(message = "type of recipe is required")
    private Type type;

    @ImageTypeAnnotation
    private MultipartFile mediaUrl;

    private Difficulty difficulty = Difficulty.MEDIUM;

    @NotBlank(message = "country origin is required")
    private String countryOrigin;
}
