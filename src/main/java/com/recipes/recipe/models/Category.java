package com.recipes.recipe.models;

import com.recipes.recipe.models.base.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "category")
public class Category extends Base {
    @Column(name = "category_title", nullable = false, unique = false)
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9\\s-_]{1,20}$", message = "category title contains invalid characters or starts with a space.")
    @Size(max = 20, message = "category title cannot exceed 20 characters")
    private String title;

    @Column(name = "category_image", nullable = false, unique = false)
    @NotBlank(message = "category image is required")
    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Recipe> recipes;
}
