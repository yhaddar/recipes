package com.recipes.recipe.services;

import com.recipes.recipe.config.S3Config;
import com.recipes.recipe.dto.CategoryDTO;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.repositories.CategoryRepository;
import com.recipes.recipe.request.CategoryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Category service Unit tests")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private S3Config s3Config;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("method for test if the category exist and return it or not")
    public void testIfCategoryExist(){
        List<Category> categories = List.of(new Category("dessert", "https://recipesyhaddar.s3.us-east-1.amazonaws.com/categories/cupcake.png", List.of()));
        when(this.categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> result = this.categoryService.index();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("method for test if the category don't exist and return exception error")
    public void testIfCategoryReturnException(){
        when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> this.categoryService.index()
        );

        assertEquals("no category found", exception.getMessage());
    }

    @Test
    @DisplayName("method for test if the category was saved")
    public void testIfCategorySaved() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "image".getBytes()
        );

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("pates");
        categoryRequest.setImage(file);

        when(s3Config.getUrl(any())).thenReturn("categories/image.png");

        CompletableFuture<ResponseEntity<String>> store = this.categoryService.store(categoryRequest);
        ResponseEntity<String> response = store.get();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("category was added with success", response.getBody());

        verify(this.s3Config).uploadFile(any(), any());
        verify(this.categoryRepository).save(any(Category.class));

    }

    @Test
    @DisplayName("method for test if the category exist before deleted")
    public void testIfCategoryExistBeforeDelete() throws Exception{
        UUID id = UUID.randomUUID();
        Category category = new Category();
        category.setId(id);
        category.setTitle("pates");
        category.setImage("https://fake-image/categories/image.png");

        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));
        CompletableFuture<ResponseEntity<String>> delete = this.categoryService.delete(id);

        ResponseEntity<String> response = delete.get();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("category deleted", response.getBody());

        verify(this.s3Config).deleteFile("https://fake-image/categories/image.png");
        verify(this.categoryRepository).deleteById(id);
    }

    @Test
    @DisplayName("method for test if return exception when category not found")
    public void testIfReturnExceptionWhenCategoryNotFound(){
        UUID id = UUID.randomUUID();
        when(this.categoryRepository.findById(id)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.categoryService.delete(id));
        assertEquals("category not found", exception.getMessage());
    }

    @Test
    @DisplayName("method for test if category not found in update")
    public void testIfCategoryNotFoundInUpdate() throws Exception {
        UUID id = UUID.randomUUID();

        when(this.categoryRepository.findById(id)).thenReturn(Optional.empty());

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle(null);
        categoryRequest.setImage(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.categoryService.update(id, categoryRequest));

        assertEquals("category not found", exception.getMessage());

        verify(this.s3Config, never()).updateFile(any(), any(), any());
        verify(this.categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("method for test the update of category without image")
    public void testIfCategoryUpdateWithoutImage() throws Exception{
        UUID id = UUID.randomUUID();

        Category category = new Category();
        category.setTitle("pates");
        category.setImage("https://fake-image/categories/image.png");

        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("pattes");
        categoryRequest.setImage(null);

        CompletableFuture<ResponseEntity<String>> update = this.categoryService.update(id, categoryRequest);
        ResponseEntity<String> response = update.get();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("category updated", response.getBody());

        verify(this.s3Config, never()).updateFile(any(), any(), any());
        verify(this.categoryRepository).save(category);
    }

    @Test
    @DisplayName("method for test the update with image")
    public void testIfCategoryUpdatedWithImage() throws Exception {
        UUID id = UUID.randomUUID();

        Category category = new Category();
        category.setTitle("pates");
        category.setImage("https://fake-image/categories/image.png");

        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "image".getBytes()
        );

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("pattes");
        categoryRequest.setImage(file);

        when(this.s3Config.getUrl(any())).thenReturn("https://fake-image/categories/new-image.png");

        CompletableFuture<ResponseEntity<String>> update = this.categoryService.update(id, categoryRequest);
        ResponseEntity<String> response = update.get();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("category updated", response.getBody());

        verify(this.s3Config).updateFile(any(), any(), any());
        verify(this.categoryRepository).save(category);
    }

}