package com.recipes.recipe.DTO.UserDTO;

import com.recipes.recipe.Model.Entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTOPostResponse {
    private String first_name;
    private String last_name;
    private String profile;

    public static UserDTOPostResponse EntityToJson(User user){
        return UserDTOPostResponse.builder()
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .profile(user.getProfile())
                .build();
    }
}