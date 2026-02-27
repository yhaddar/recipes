package com.recipes.recipe.controllers;

import com.recipes.recipe.dto.CategoryDTO;
import com.recipes.recipe.request.CategoryRequest;
import com.recipes.recipe.services.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private CacheManager cacheManager;

    @Test
    @DisplayName("method for test if the index function return the service category")
    void testIndexReturnsCategory() throws Exception {
        List<CategoryDTO> categories = List.of(
                new CategoryDTO(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), "fruit", "https://fake-image/categories/image.png"),
                new CategoryDTO(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), "pattes", "https://fake-image/categories/image.png"),
                new CategoryDTO(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), "breakfast", "https://fake-image/categories/image.png")
        );

        when(this.categoryService.index()).thenReturn(categories);

        this.mockMvc.perform(get("/api/category").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].title").value("fruit"))
                .andExpect(jsonPath("$[1].title").value("pattes"))
                .andExpect(jsonPath("$[2].title").value("breakfast"));

    }

    @Test
    @DisplayName("method for test the store controller")
    void testStore() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image".getBytes()
        );

        when(this.categoryService.store(any(CategoryRequest.class))).thenReturn(CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body("category was added with success")));

        MvcResult mvcResult = this.mockMvc.perform(multipart("/api/category/add")
                        .file(file)
                        .param("title", "breakfast")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(content().string("category was added with success"));

    }

    @Test
    @DisplayName("method for test if the category was updated")
    void testUpdate() throws Exception {
        UUID id = UUID.randomUUID();

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image".getBytes()
        );

        when(this.categoryService.update(any(UUID.class), any(CategoryRequest.class))).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok("category updated")));

        MvcResult mvcResult = this.mockMvc.perform(multipart("/api/category/update")
                .file(file)
                .param("title", "update title")
                        .param("id", id.toString())
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                .content(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("category updated"));
    }
    @Test
    @DisplayName("method for test delete category")
    void testDelete() throws Exception{
        UUID id = UUID.randomUUID();

        when(this.categoryService.delete(id)).thenReturn(CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NO_CONTENT).body("category deleted")));

        MvcResult mvcResult = this.mockMvc.perform(delete("/api/category/delete")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(request().asyncStarted())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
                .andExpect(content().string("category deleted"));

    }
}