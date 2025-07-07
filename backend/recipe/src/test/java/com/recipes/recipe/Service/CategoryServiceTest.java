package com.recipes.recipe.Service;

import com.recipes.recipe.DTO.CategoryDTO.CategoryDTORequest;
import com.recipes.recipe.DTO.CategoryDTO.CategoryUpdateRequest;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Repository.CategoryRepository;
import com.recipes.recipe.Service.UploadToS3.S3Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private S3Service s3Service;

    @Captor
    ArgumentCaptor<Category> argumentCaptor;

    @BeforeEach
    void setup(){
        categoryService = new CategoryService(this.categoryRepository, this.s3Service);
    }


    @Test
    void store() throws ExecutionException, InterruptedException {
        // given

        MockMultipartFile uploadFile = new MockMultipartFile(
                "image",
                "image.png",
                "text/plain",
                "hello mockmvc".getBytes()
        );

        when(this.s3Service.uploadFile(uploadFile, "some/path")).thenReturn("category.png");


        CategoryDTORequest categoryDTORequest = new CategoryDTORequest();
        categoryDTORequest.setTitle("category 1");
        categoryDTORequest.setImage(uploadFile);

        // when
        CompletableFuture<ResponseEntity<String>> response = this.categoryService.store(categoryDTORequest);
        ResponseEntity<?> getResponse = response.get();

        assertThat(HttpStatus.CREATED).isEqualTo(getResponse.getStatusCode());
        assertThat(getResponse.getBody()).isEqualTo("the category was created");

        // then
        verify(this.categoryRepository).save(this.argumentCaptor.capture());
        Category capturedCategory = this.argumentCaptor.getValue();

        assertThat(capturedCategory.getTitle()).isEqualTo(categoryDTORequest.getTitle());
    }

    @Test
    void index() {
        // given
        when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        ResponseEntity<?> response = this.categoryService.index();

        assertThat(HttpStatus.NOT_FOUND).isEqualTo(response.getStatusCode());
        assertThat(response.getBody()).isEqualTo("new categories will be added soon.");

        verify(this.categoryRepository).findAll();
    }

    @Test
    @Disabled
    void delete() {
    }

    @Test
    void update() {
        // given
        UUID id = UUID.fromString("89aa16f3-f71a-4d93-bee1-da678f208487");
        String title = "new category";
        MockMultipartFile updateImage = new MockMultipartFile(
          "image",
          "image2.png",
          "text/plain",
          "hello mockmvc".getBytes()
        );

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setTitle(title);
        categoryUpdateRequest.setImage(updateImage);

        // when
        when(this.categoryRepository.findById(id)).thenReturn(Optional.empty());

        // then
        ResponseEntity<?> response = this.categoryService.update(categoryUpdateRequest, id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("category not found");

        verify(this.categoryRepository, never()).save(any());
    }

    @AfterEach
    void tearDown(){
        this.categoryRepository.deleteAll();
    }
}