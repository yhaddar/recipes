package com.recipes.recipe.DTO.BlogDTO;

import com.recipes.recipe.DTO.BaseDTO;
import com.recipes.recipe.DTO.UserDTO.UserDTOPostResponse;
import com.recipes.recipe.Model.Entity.Blog;
import com.recipes.recipe.Model.Entity.Recipe;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BlogShowDTOResponse extends BaseDTO {
    private String title;
    private String image;
    private String description;
    private UserDTOPostResponse user;


    public static BlogShowDTOResponse EntityToJson(Blog blog){
        return BlogShowDTOResponse.builder()
            .id(blog.getId())
            .title(blog.getTitle())
            .image("https://recipesyhaddar.s3.us-east-1.amazonaws.com/" + blog.getImage())
            .description(blog.getDescription())
            .user(UserDTOPostResponse.EntityToJson(blog.getUser_id()))
            .created_at(blog.getCreated_at())
            .updated_at(blog.getUpdated_at())
            .build();
    }
}