package com.recipes.recipe.models;

import com.recipes.recipe.enums.Gender;
import com.recipes.recipe.models.base.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@Data
public class User extends Base {
    @Column(nullable = false, unique = false, name = "first_name")
    @NotNull(message = "first name should not be null")
    @NotEmpty(message = "first name should not be empty")
    @NotBlank(message = "first name is required")
    @Pattern(regexp = "^[a-z]+$", message = "first name must contain only letters without spaces or special characters")
    private String first_name;

    @Column(nullable = false, unique = false, name = "last_name")
    @NotNull(message = "last name should not be null")
    @NotEmpty(message = "last name should not be empty")
    @NotBlank(message = "last name is required")
    @Pattern(regexp = "^[a-z]+$", message = "last name must contain only letters without spaces or special characters")
    private String last_name;

    @Column(nullable = true, unique = false, name = "profile_url")
    private String profile_url = "https://recipesyhaddar.s3.us-east-1.amazonaws.com/users/blank-profile-picture-973460_1280.webp";

    @Column(nullable = true, unique = false, name = "bio")
    @Pattern(regexp = "^[\\\\p{L}0-9 .,!?'-\\\\p{So}*]*$", message = "Bio can contain letters, numbers, spaces, emojis, and basic punctuation (.,!?'-)\"\n")
    @Size(max = 200, message = "Bio cannot exceed 300 characters")
    private String bio;

    @Column(nullable = false, unique = true, name = "email")
    @NotNull(message = "email should not be null")
    @NotEmpty(message = "email should not be empty")
    @NotBlank(message = "email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Please provide a valid email address")
    private String email;

    @Column(nullable = false, unique = true, name = "password")
    @NotNull(message = "password should not be null")
    @NotEmpty(message = "password should not be empty")
    @NotBlank(message = "password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$", message = "Password must be at least 8 characters long, with 1 uppercase, 1 lowercase, 1 number, and 1 special character (!@#$%^&*())")
    @Min(value = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Column(nullable = true, unique = false, name = "phone_whatsapp")
    @Size(max = 10, min = 10, message = "phone whatsapp invalid")
    private String phone_whatsapp;

    @Column(unique = false, nullable = false, name = "gender")
    @Enumerated(value = EnumType.STRING)
    @NotBlank(message = "gender is required")
    private Gender gender;

    @JoinColumn(name = "address_id", nullable = true, referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private boolean email_verified = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> recipes;
}
