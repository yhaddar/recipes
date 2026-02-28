package com.recipes.recipe.request;

import com.recipes.recipe.enums.Gender;
import com.recipes.recipe.models.Address;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "first name is required")
    @Pattern(regexp = "^[a-z]+$", message = "first name must contain only letters without spaces or special characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Pattern(regexp = "^[a-z]+$", message = "last name must contain only letters without spaces or special characters")
    private String lastName;

    private String profileUrl = "https://recipesyhaddar.s3.us-east-1.amazonaws.com/users/blank-profile-picture-973460_1280.webp";

    @Pattern(regexp = "^[\\\\p{L}0-9 .,!?'-\\\\p{So}*]*$", message = "Bio can contain letters, numbers, spaces, emojis, and basic punctuation (.,!?'-)\"\n")
    @Size(max = 200, message = "Bio cannot exceed 300 characters")
    private String bio;

    @NotBlank(message = "email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$", message = "Password must be at least 8 characters long, with 1 uppercase, 1 lowercase, 1 number, and 1 special character (!@#$%^&*())")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "phone whatsapp is required")
    @Size(max = 10, min = 10, message = "phone whatsapp invalid")
    private String phoneWhatsapp;

    @NotNull(message = "gender is required")
    private Gender gender;

    @NotNull(message = "address is required")
    private AddressRequest address;
}
