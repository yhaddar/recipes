package com.recipes.recipe.DTO.BlogDTO;

import com.recipes.recipe.DTO.BaseDTO;
//import com.recipes.recipe.DTO.UserDTO.UserDTOPostResponse;
import com.recipes.recipe.Model.Entity.Blog;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BlogDTOResponse extends BaseDTO {
    private String title;
    private String image;
    private String description;
//    private UserDTOPostResponse user;

    public static BlogDTOResponse EntityToJson(Blog blog){
        return BlogDTOResponse.builder()
            .id(blog.getId())
            .title(blog.getTitle())
            .image("https://recipesyhaddar.s3.us-east-1.amazonaws.com/" + blog.getImage())
//            .user(UserDTOPostResponse.EntityToJson(blog.getUser_id()))
            .description(blog.getDescription())
            .created_at(blog.getCreated_at())
            .updated_at(blog.getUpdated_at())
            .build();
    }
}