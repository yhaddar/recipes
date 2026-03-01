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
    private String first_name;

    @Column(nullable = false, unique = false, name = "last_name")
    private String last_name;

    @Column(nullable = true, unique = false, name = "profile_url")
    private String profile_url = "https://recipesyhaddar.s3.us-east-1.amazonaws.com/users/blank-profile-picture-973460_1280.webp";

    @Column(nullable = true, unique = false, name = "bio")
    private String bio;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, unique = true, name = "password")
    private String password;

    @Column(nullable = true, unique = false, name = "phone_whatsapp")
    private String phone_whatsapp;

    @Column(unique = false, nullable = false, name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = true, referencedColumnName = "id")
    private Address address;

    private boolean email_verified = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> recipes;
}
