package com.recipes.recipe.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddressRequest {
    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "country is required")
    private String country;

    private String state;

    @Min(value = 4, message = "postal code is too short")
    @Max(value = 6,  message = "postal code is too long")
    @NotBlank(message = "postal code required")
    private int postalCode;
}
