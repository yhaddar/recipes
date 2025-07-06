package com.recipes.recipe.Model.Entity;

import com.recipes.recipe.Model.BaseModel;
import jakarta.persistence.*;
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
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title")
})
public class Category extends BaseModel {
    @Column(unique = false)
    @NotBlank(message = "the image was not be empty")
    private String image;

    @NotBlank(message = "the title was not be empty")
    @Size(min = 3, max = 20, message = "this title must be between 3 and 20 characters")
    private String title;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;
}