package com.recipes.recipe.Models.Entity;

import com.recipes.recipe.Enum.Role;
import com.recipes.recipe.Models.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseModel {
    @Column(unique = false)
    @NotBlank(message = "the first name not be empty")
    @Size(min = 3, max = 10, message = "the first name must be between 3 and 10 characters")
    private String first_name;

    @Column(unique = false)
    @NotBlank(message = "the last name not be empty")
    @Size(min = 3, max = 10, message = "the last name must be between 3 and 10 characters")
    private String last_name;

    @Column(unique = true)
    @Size(min = 4, max = 8, message = "this username is too long")
    private String username;

    @Column(unique = true)
    @Email(message = "the email is invalide")
    private String email;

    @Column(unique = false)
    @NotBlank(message = "the password not be empty")
    @Size(min = 8, message = "the password must be mininum 8 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;

    public User(){
        this.role = Role.CLIENT;
    }
}