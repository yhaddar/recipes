package com.recipes.recipe.DTO;

import com.recipes.recipe.Enum.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    @Valid

    @NotEmpty(message = "the first name was not be empty")
    @Pattern(regexp = "^[a-zA-Z]{3,10}$", message = "this first name is invalide")
    @Size(min = 3, max = 10, message = "the first name must be between 3 and 10 characters")
    private String first_name;

    @NotEmpty(message = "the last name was not be empty")
    @Pattern(regexp = "^[a-zA-Z]{3,10}$", message = "this last name is invalide")
    @Size(min = 3, max = 10, message = "the last name must be between 3 and 10 characters")
    private String last_name;

    @NotEmpty(message = "the username was not be empty")
    @Pattern(regexp = "^[a-z]{4,8}$", message = "this username is invalide")
    @Size(min = 3, max = 10, message = "the username must be between 3 and 10 characters")
    private String username;

    @NotEmpty(message = "the email was not be empty")
    @Email(message = "this email is invalide")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "this email is invalide")
    private String email;

    @NotEmpty(message = "the password was not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "this password is invalide")
    @Size(min = 8, message = "the password must be mininum 8 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;
}