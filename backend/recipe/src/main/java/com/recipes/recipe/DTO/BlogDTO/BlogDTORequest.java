package com.recipes.recipe.DTO.BlogDTO;

import com.recipes.recipe.Annotation.FileType;
//import com.recipes.recipe.Annotation.UserExist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogDTORequest {
    @Valid

    @NotEmpty(message = "the title was not be empty")
    @NotBlank(message = "the title was not be null")
    @NotNull(message = "the title was not be null")
    @Pattern(regexp = "^[a-zA-Z-_&\\s]{8,100}$", message = "this title invalide")
    private String title;

    @NotNull(message = "the image was not be null")
    @FileType(message = "the type of image invalide, import image with png or jpg or jpeg or webp")
    private MultipartFile image;

    @NotBlank(message = "the description was not be empty")
    @NotNull(message = "the description was not be null")
    @NotEmpty(message = "the description was not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9.,!?()'\"\\s:&_-]{20,255}$", message = "this description was invalide")
    private String description;

//    @NotNull(message = "the user was not be null")
//    @UserExist(message = "this user is not exist")
//    private UUID user;
}