package com.recipes.recipe.Models.Entity;

import com.recipes.recipe.Models.BaseModel;
import jakarta.persistence.*;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "categories")
public class Category extends BaseModel {
    @Column(unique = false, columnDefinition = "TEXT")
    @NotBlank(message = "the image was not be empty")
    @Lob
    private String image;

    @Column(unique = false)
    @NotBlank(message = "the title was not be empty")
    @Size(min = 3, max = 20)
    private String title;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;
}