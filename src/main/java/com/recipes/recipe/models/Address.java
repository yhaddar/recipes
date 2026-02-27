package com.recipes.recipe.models;

import com.recipes.recipe.models.base.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address extends Base {

    @Column(unique = false, nullable = false, name = "city")
    @NotBlank(message = "city is required")
    @NotEmpty(message = "city should not be empty")
    @NotNull(message = "city should not be null")
    private String city;

    @Column(unique = false, nullable = false, name = "country")
    @NotBlank(message = "country is required")
    @NotEmpty(message = "country should not be empty")
    @NotNull(message = "country should not be null")
    private String country;

    @Column(unique = false, nullable = true, name = "state")
    private String state;

    @Column(unique = false, nullable = true, name = "postal_code")
    @Min(value = 4, message = "postal code is too short")
    @Max(value = 6,  message = "postal code is too long")
    private int postal_code;

    @OneToOne(mappedBy = "address")
    private User user;
}
