package com.recipes.recipe.DTO.CategoryDTO;

import com.recipes.recipe.DTO.BaseDTO;
import com.recipes.recipe.Model.Entity.Category;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class CategoryDTOResponse extends BaseDTO {
    private String image;
    private String title;

    public static CategoryDTOResponse entityToJson(Category category){
        return CategoryDTOResponse.builder()
                .id(category.getId())
                .image(category.getImage())
                .title(category.getTitle())
                .created_at(category.getCreated_at())
                .updated_at(category.getUpdated_at())
                .build();
    }
}