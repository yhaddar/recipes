package com.recipes.recipe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.recipes.recipe.models.Category;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDTO implements Serializable {
    private UUID id;
    @JsonFormat(pattern = "MMM dd, yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "MMM dd, yyyy")
    private LocalDateTime updatedAt;
    private String title;
    private String image;

    public static CategoryDTO toJSON(Category category){
        return CategoryDTO.builder()
                .title(category.getTitle())
                .image(category.getImage())
                .id(category.getId())
                .createdAt(category.getCreated_at())
                .updatedAt(category.getUpdated_at())
                .build();
    }
}
