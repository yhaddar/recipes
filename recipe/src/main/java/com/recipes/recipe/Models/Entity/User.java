package com.recipes.recipe.Models.Entity;

import com.recipes.recipe.Models.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "users")
public class User extends BaseModel {
    @Column(unique = false)
    @NotNull(message = "the first name not be empty")
    @Size(min = 3, max = 10, message = "the first name must be between 3 and 10 characters")
    @Pattern(regexp = "^[A-Za-z]{3,10}$", message = "the first name invalide")
    private String first_name;

    @Column(unique = false)
    @NotNull(message = "the last name not be empty")
    @Size(min = 3, max = 10, message = "the last name must be between 3 and 10 characters")
    @Pattern(regexp = "^[A-Za-z]{3,10}$", message = "the last name invalide")
    private String last_name;

    @Column(unique = true)
    @Email(message = "the email is invalide")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$", message = "the email is invalide")
    private String email;

    @Column(unique = false)
    @NotNull(message = "the password not be empty")
    @Size(min = 8, message = "the password must be mininum 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "the last name invalide")
    private String password;
}