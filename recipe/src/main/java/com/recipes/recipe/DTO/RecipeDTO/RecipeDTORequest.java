package com.recipes.recipe.DTO.RecipeDTO;

import com.recipes.recipe.Annotation.CategoryExist;
import com.recipes.recipe.Annotation.FileType;
import com.recipes.recipe.Annotation.UserExist;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Model.Entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeDTORequest {
    @Valid
    @NotEmpty(message = "the title was not be empty")
    @NotBlank(message = "the title was not be null")
    @NotNull(message = "the title was not be null")
    @Pattern(regexp = "^[a-zA-Z-_&\\s]{8,20}$", message = "this title invalide")
    private String title;

    @FileType(message = "the type of image invalide, import image with png or jpg or jpeg or webp")
    private MultipartFile image;

    @NotBlank(message = "the description was not be empty")
    @NotNull(message = "the description was not be null")
    @NotEmpty(message = "the description was not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9.,!?()'\"\\s:&_-]{20,255}$", message = "this description was invalide")
    private String description;

    @NotBlank(message = "the preparation time was not be empty")
    @NotEmpty(message = "the preparation time was not be empty")
    @NotNull(message = "the preparation time was not be null")
    @Min(1)
    private String prep_time;

    @NotBlank(message = "the cooking time was not be empty")
    @NotEmpty(message = "the cooking time was not be empty")
    @NotNull(message = "the cooking time was not be null")
    @Min(1)
    private String cook_time;

    @NotNull(message = "the category was not be null")
//    @NotEmpty(message = "the category was not be empty")
//    @NotBlank(message = "the category was not be empty")
    @CategoryExist(message = "this category is not exist")
    private Category category;

//    @NotBlank(message = "the user was not be empty")
//    @NotEmpty(message = "the user was not be empty")
    @NotNull(message = "the user was not be null")
    @UserExist(message = "this user is not exist")
    private User user;
}