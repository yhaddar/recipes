package com.recipes.recipe.Model.Entity;

import com.recipes.recipe.Model.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "recipe")
public class Recipe extends BaseModel {
    @Column(unique = false)
    @NotBlank(message = "the title not be empty")
    @Size(min = 8, max = 20, message = "this title must be between 8 and 30 characters")
    private String title;

    @Column(unique = false, columnDefinition = "TEXT")
    @NotBlank(message = "the image not be empty")
    private String image;

    @Column(unique = false, columnDefinition = "TEXT")
    @NotBlank(message = "the description was not be empty")
    @Size(min = 20, max = 255, message = "this description must be between 20 and 255 characters")
    private String description;

    @Column(unique = false)
    @NotNull(message = "the preparation time was notbe empty")
    @PositiveOrZero
    @Min(1)
    private Integer prep_time;

    @Column(unique = false)
    @NotNull(message = "the cooking time was not be empty")
    @PositiveOrZero
    @Min(1)
    private Integer cook_time;

    @Column(unique = false, name = "user_id")
    @NotNull(message = "the user was not be empty")
    private String user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}