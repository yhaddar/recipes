package com.recipes.recipe.Service;

import com.recipes.recipe.Client.UserClient;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTORequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOResponse;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Model.Entity.Recipe;
import com.recipes.recipe.Repository.CategoryRepository;
import com.recipes.recipe.Repository.RecipeRepository;
import com.recipes.recipe.Service.UploadToS3.S3Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RecipeServiceTest {

    @Mock
    private static RecipeDTOResponse recipeDTOResponse;
    @InjectMocks
    RecipeService recipeService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private S3Service s3Service;
    private UUID categoryId;
    @Mock
    private UserClient userClient;

    private MockMultipartFile uploadFile() {

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "hello world".getBytes()
        );
        when(this.s3Service.uploadFile(file, "some/path")).thenReturn("image.png");

        return file;

    }

    @BeforeEach
    void setup(){
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setTitle("category 1");
        category.setImage("image.png");
        when(this.categoryRepository.save(category)).thenReturn(category);

        Category saveCategory = this.categoryRepository.save(category);
        this.categoryId = saveCategory.getId();

    }


    private RecipeDTORequest createNewRecipeDTO(){
        RecipeDTORequest recipeDTORequest = new RecipeDTORequest();
        recipeDTORequest.setTitle("category 1");
        recipeDTORequest.setImage(uploadFile());
        recipeDTORequest.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
        recipeDTORequest.setCook_time(15);
        recipeDTORequest.setPrep_time(20);
        recipeDTORequest.setCategory_id(categoryId);
        recipeDTORequest.setUser("42a20af9-c378-4429-bae6-2ac79188b355");
        return recipeDTORequest;
    }

    private Category category(){
        Category category = new Category();
        category.setId(this.categoryId);
        category.setTitle("category 1");
        category.setImage("image.png");
        return category;
    }

    @Test
    @Disabled
    void index() {
        // given
//        when(this.recipeService.index()).thenReturn(Collections.emptyList());

        // when


        // then
    }

    @Test
    void shouldReturnCategoryNotFound() throws ExecutionException, InterruptedException {

        // given
        when(this.categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        RecipeDTORequest recipeDTORequest = new RecipeDTORequest();
        recipeDTORequest.setTitle("category 1");
        recipeDTORequest.setImage(uploadFile());

        // when
        CompletableFuture<ResponseEntity<String>> recipe = this.recipeService.store(recipeDTORequest);
        ResponseEntity<String> result = recipe.get();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("no category found");

        // then
        verify(this.categoryRepository, never()).findById(categoryId);

    }


    @Test
    void shouldReturnUserNotFound() throws ExecutionException, InterruptedException {

        when(this.userClient.verifierUser(anyString())).thenReturn(false);

        when(this.categoryRepository.findById(categoryId)).thenReturn(Optional.of(category()));

        RecipeDTORequest recipeDTORequest = createNewRecipeDTO();


        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDTORequest.getTitle());
        recipe.setCook_time(recipeDTORequest.getCook_time());
        recipe.setPrep_time(recipeDTORequest.getPrep_time());
        recipe.setCategory(category());
        recipe.setDescription(recipeDTORequest.getDescription());
        recipe.setUser(recipeDTORequest.getUser());

        CompletableFuture<ResponseEntity<String>> savedrecipe = this.recipeService.store(recipeDTORequest);

        assertThat(savedrecipe.get().getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(savedrecipe.get().getBody()).isEqualTo("user don't exists");

        verify(this.userClient).verifierUser(anyString());
    }


    @Test
    void shouldReturnCreatedInAddNewRecipe() throws ExecutionException, InterruptedException {

        RecipeDTORequest recipeDTORequest = createNewRecipeDTO();

        when(this.categoryRepository.findById(categoryId)).thenReturn(Optional.of(category()));


        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDTORequest.getTitle());
        recipe.setCook_time(recipeDTORequest.getCook_time());
        recipe.setPrep_time(recipeDTORequest.getPrep_time());
        recipe.setCategory(category());
        recipe.setDescription(recipeDTORequest.getDescription());
        recipe.setUser(recipeDTORequest.getUser());

        when(this.recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(this.userClient.verifierUser(anyString())).thenReturn(true);

        CompletableFuture<ResponseEntity<String>> saveRecipe = this.recipeService.store(recipeDTORequest);

        assertThat(saveRecipe.get().getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saveRecipe.get().getBody()).isEqualTo("the recipe was created");


        verify(this.recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void delete() {
    }

    @Test
    @Disabled
    void search() {
    }

    @Test
    @Disabled
    void show() {
    }

    @AfterEach
    void tearAll(){
        this.categoryRepository.deleteAll();
        this.recipeRepository.deleteAll();
    }
}