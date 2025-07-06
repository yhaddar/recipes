package com.recipes.recipe.Model.Entity;

import com.recipes.recipe.Model.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "blogs")
public class Blog extends BaseModel {
    @Column(unique = false)
    @NotBlank(message = "the title was not be empty")
    @NotNull(message = "the title was not be null")
    @NotEmpty(message = "the title was not be empty")
    private String title;

    @Column(unique = false)
    @NotBlank(message = "the title was not be empty")
    @NotNull(message = "the title was not be null")
    @NotEmpty(message = "the title was not be empty")
    private String description;

    @Column(unique = false, columnDefinition = "TEXT")
    @NotNull(message = "the title was not be null")
    private String image;
}