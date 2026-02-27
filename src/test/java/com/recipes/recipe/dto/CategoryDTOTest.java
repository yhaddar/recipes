package com.recipes.recipe.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.recipes.recipe.models.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDTOTest {

    @Test
    @DisplayName("method for test the serializable of date")
    void testSerialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(UUID.randomUUID())
                .title("breakfast")
                .image("https://fake-image/categories/image.png")
                .createdAt(LocalDateTime.of(2026, 2, 23, 18, 7))
                .updatedAt(LocalDateTime.of(2026, 2, 23, 18, 7))
                .build();

        String json = mapper.writeValueAsString(categoryDTO);
        assertTrue(json.contains("Feb 23, 2026"));
    }

    @Test
    @DisplayName("method for test if category return as json")
    void testToJson(){
        UUID id = UUID.randomUUID();
        Category category = new Category();
        category.setId(id);
        category.setTitle("breakfast");
        category.setImage("https://fake-image/categories/image.png");

        CategoryDTO categoryDTO = CategoryDTO.toJSON(category);

        assertEquals(id, categoryDTO.getId());
        assertEquals("breakfast", categoryDTO.getTitle());
        assertEquals("https://fake-image/categories/image.png", categoryDTO.getImage());

    }

}