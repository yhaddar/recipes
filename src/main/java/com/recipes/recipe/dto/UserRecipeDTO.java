package com.recipes.recipe.dto;

import com.recipes.recipe.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRecipeDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String profileUrl;

    public static UserRecipeDTO toJSON(User user){
        return UserRecipeDTO.builder()
                .firstName(user.getFirst_name())
                .lastName(user.getLast_name())
                .profileUrl(user.getProfile_url())
                .id(user.getId())
                .build();
    }

}
