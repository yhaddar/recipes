package com.recipes.recipe.DTO.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String first_name;
    private String last_name;
    private String profile;

    public String getFirstName() {
        return first_name;
    }
}
