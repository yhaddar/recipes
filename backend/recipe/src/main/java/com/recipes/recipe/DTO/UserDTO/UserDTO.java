package com.recipes.recipe.DTO.UserDTO;

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
