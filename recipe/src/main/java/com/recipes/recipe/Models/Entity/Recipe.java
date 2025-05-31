package com.recipes.recipe.Models.Entity;

import com.recipes.recipe.Models.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "recipes")
public class Recipe extends BaseModel {
    @Column(unique = false)
    @NotBlank(message = "the title not be empty")
    @Size(min = 8, max = 20, message = "this title must be between 8 and 30 characters")
    private String title;

    @Column(unique = false, columnDefinition = "TEXT")
    @NotBlank(message = "the image not be empty")
    @Lob
    private String image;

    @Column(unique = false, columnDefinition = "TEXT")
    @NotBlank(message = "the description was not be empty")
    @Size(min = 20, max = 255, message = "this description must be between 20 and 255 characters")
    @Lob
    private String description;

    @Column(unique = false)
    @NotBlank(message = "the preparation time was notbe empty")
    @PositiveOrZero
    @Min(1)
    private Integer prep_time;

    @Column(unique = false)
    @NotBlank(message = "the cooking time was notbe empty")
    @PositiveOrZero
    @Min(1)
    private Integer cook_time;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}