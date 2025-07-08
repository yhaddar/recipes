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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
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
    void shouldReturnErrorDataIntegrityInAddCategory() throws ExecutionException, InterruptedException {

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

        when(this.categoryRepository.save(any(Category.class))).thenThrow(DataIntegrityViolationException.class);

        CompletableFuture<ResponseEntity<String>> response = this.categoryService.store(categoryDTORequest);

        assertThat(HttpStatus.CONFLICT).isEqualTo(response.get().getStatusCode());
        assertThat(response.get().getBody()).isEqualTo("failed to create category with title " + categoryDTORequest.getTitle());

        verify(this.categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldReturnExceptionErrorForAddCategory() throws ExecutionException, InterruptedException {
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

        when(this.categoryRepository.save(any(Category.class)))
                .thenThrow(new RuntimeException("error in the server, try in another time"));

        CompletableFuture<ResponseEntity<String>> response = this.categoryService.store(categoryDTORequest);

        assertThat(HttpStatus.INTERNAL_SERVER_ERROR).isEqualTo(response.get().getStatusCode());
        assertThat(response.get().getBody()).isEqualTo("error in the server, try in another time");

        verify(this.categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldVerifyIfCategoryWasEmptyInIndex() {
        // given
        when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        ResponseEntity<?> response = this.categoryService.index();

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());

        Map<String, String> message = new HashMap<>();
        message.put("message", "new categories will be added soon.");
        assertThat(response.getBody()).isEqualTo(message);

        verify(this.categoryRepository).findAll();
    }

    @Test
    void shouldVerifyIfCategoryWasNotEmpty() {

        List<Category> categories = List.of(new Category());
        when(this.categoryRepository.findAll()).thenReturn(categories);

        ResponseEntity<?> response = this.categoryService.index();
        List<Category> result = (List<Category>) response.getBody();

        assertThat(result).isNotEmpty();

        verify(this.categoryRepository).findAll();
    }

    @Test
    void delete() {
        UUID id = UUID.fromString("89aa16f3-f71a-4d93-bee1-da678f208487");

        Category category = new Category();
        category.setId(id);
        category.setTitle("category 1");
        category.setImage("image.png");

        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));

        ResponseEntity<?> result = this.categoryService.delete(id);
        assertThat(HttpStatus.NO_CONTENT).isEqualTo(result.getStatusCode());
        assertThat(result.getBody()).isEqualTo("the category was removed");

        verify(this.categoryRepository).delete(category);
    }

    @Test
    void shouldReturnCategoryNotFound() {
        UUID id = UUID.fromString("89aa16f3-f71a-4d93-bee1-da678f208487");

        // given
        when(this.categoryRepository.findById(id)).thenThrow(new RuntimeException("category not found."));

        // when
        ResponseEntity<?> category = this.categoryService.delete(id);

        // then
        assertThat(category.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(category.getBody()).isEqualTo("category not found.");

        verify(this.categoryRepository, never()).deleteById(id);
    }

    @Test
    void shouldCategoryReturnErrorDataIntegrityInDelete() {
        UUID id = UUID.fromString("89aa16f3-f71a-4d93-bee1-da678f208487");

        // given
        when(this.categoryService.delete(id)).thenThrow(new DataIntegrityViolationException("This category cannot be deleted because it is related to several recipes."));

        // when
        ResponseEntity<?> category = this.categoryService.delete(id);
        assertThat(HttpStatus.CONFLICT).isEqualTo(category.getStatusCode());
        assertThat(category.getBody()).isEqualTo("This category cannot be deleted because it is related to several recipes.");

        // then
        verify(this.categoryRepository, never()).deleteById(id);
    }

    @Test
    @Disabled
    void shouldReturnExceptionErrorInDelete() {
        UUID id = UUID.fromString("89aa16f3-f71a-4d93-bee1-da678f208487");

        // given
//        when(this.categoryService.delete(id)).thenThrow(new RuntimeException("error in the server, try in another time"));

        doThrow(new Exception("error in the server, try in another time"))
                .when(this.categoryRepository).deleteById(id);

        // when
        ResponseEntity<?> category = this.categoryService.delete(id);
        assertThat(HttpStatus.INTERNAL_SERVER_ERROR).isEqualTo(category.getStatusCode());
        assertThat(category.getBody()).isEqualTo("error in the server, try in another time");

        // then
        verify(this.categoryRepository, never()).deleteById(id);
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


    @Test
    void shouldReturnExistCategoryInUpdate() {

        UUID id = UUID.fromString("89aa16f3-f71a-4d93-bee1-da678f208487");

        MockMultipartFile uploadFile = new MockMultipartFile(
                "image",
                "image.png",
                "text/plain",
                "hello mockmvc".getBytes()
        );

        when(this.s3Service.uploadFile(uploadFile, "categories")).thenReturn("category.png");

        Category category = new Category();
        category.setId(id);
        category.setTitle("new category");
        category.setImage("image.png");

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setTitle("new category");
        categoryUpdateRequest.setImage(uploadFile);

        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));

        ResponseEntity<?> category_exist = this.categoryService.update(categoryUpdateRequest, id);

        assertThat(HttpStatus.OK).isEqualTo(category_exist.getStatusCode());
        assertThat(category.getTitle()).isEqualTo(categoryUpdateRequest.getTitle());
        assertThat(category.getImage()).isEqualTo("category.png");

        verify(this.categoryRepository).save(any());

    }

    @Test
    void shouldVerifierIfCategoryNotFoundInUpdate() {

        when(this.categoryRepository.findById(any())).thenReturn(Optional.empty());

        MockMultipartFile uploadFile = new MockMultipartFile(
                "image",
                "image.png",
                "text/plain",
                "hello mockmvc".getBytes()
        );

        when(this.s3Service.uploadFile(uploadFile, "categories")).thenReturn("category.png");

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setTitle("new category");
        categoryUpdateRequest.setImage(uploadFile);

        ResponseEntity<?> category = this.categoryService.update(categoryUpdateRequest, any());
        assertThat(category.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(category.getBody()).isEqualTo("category not found");

        verify(this.categoryRepository, never()).save(any());

    }

    @AfterEach
    void tearDown(){
        this.categoryRepository.deleteAll();
    }
}