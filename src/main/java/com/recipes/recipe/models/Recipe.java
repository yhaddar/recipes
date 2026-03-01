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
    private String recipe_title;

    @Column(nullable = false, unique = false, name = "description")
    @Size(max = 500, message = "description cannot exceed 500 characters")
    private String description;

    @Column(nullable = false, unique = false, name = "cooking_time")
    private double cooking_time;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "media_url", nullable = false, unique = false)
    private String media_url;

    @Column(name = "difficulty", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty = Difficulty.MEDIUM;

    @Column(name = "country_origin", nullable = false, unique = false)
    private String country_origin;
}
