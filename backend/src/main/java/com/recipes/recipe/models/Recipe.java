package com.recipes.recipe.models;
import com.recipes.recipe.enums.Difficulty;
import com.recipes.recipe.enums.Type;
import com.recipes.recipe.models.base.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe")
@Data
public class Recipe extends Base {
    @Column(nullable = false, unique = false, name = "recipe_title")
    @NotNull(message = "recipe title should not be null")
    @NotEmpty(message = "recipe title should not be empty")
    @NotBlank(message = "recipe title is required")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9\\\\s_-]*$", message = "recipe title must contain only letters with spaces and without special characters")
    private String recipe_title;

    @Column(nullable = false, unique = false, name = "description")
    @NotNull(message = "description should not be null")
    @NotEmpty(message = "description should not be empty")
    @NotBlank(message = "description is required")
    @Pattern(regexp = "^[\\\\p{L}0-9 .,!?'-\\\\p{So}*]*$", message = "recipe title must contain only letters with spaces and without special characters")
    @Max(value = 500, message = "description cannot exceed 500 characters")
    private String description;

    @Column(nullable = false, unique = false, name = "cooking_time")
    @NotNull(message = "cooking time should not be null")
    @NotEmpty(message = "cooking time should not be empty")
    @NotBlank(message = "cooking time is required")
    private double cooking_time;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "category should be not empty")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "type of recipe is required")
    private Type type;

    @Column(name = "media_url", nullable = false, unique = false)
    @NotNull(message = "media file should not be null")
    @NotEmpty(message = "media file should not be empty")
    @NotBlank(message = "media file is required")
    private String media_url;

    @Column(name = "difficulty", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty = Difficulty.MEDIUM;

    @Column(name = "country_origin", nullable = false, unique = false)
    @NotNull(message = "country origin should not be null")
    @NotEmpty(message = "country origin should not be empty")
    @NotBlank(message = "country origin is required")
    private String country_origin;
}
