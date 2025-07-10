package com.recipes.recipe.Service;

import com.recipes.recipe.Client.UserClient;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTORequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOResponse;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOUpdateRequest;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Model.Entity.Recipe;
import com.recipes.recipe.Repository.CategoryRepository;
import com.recipes.recipe.Repository.RecipeRepository;
import com.recipes.recipe.Service.UploadToS3.S3Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import static org.assertj.core.api.Assertions.assertThat;
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
    @Mock
    private RecipeServiceCache recipeServiceCache;

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

    private RecipeDTOUpdateRequest updateRecipeDTO(){
        RecipeDTOUpdateRequest recipeDTORequest = new RecipeDTOUpdateRequest();
        recipeDTORequest.setTitle("category 1");
        recipeDTORequest.setImage(uploadFile());
        recipeDTORequest.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
        recipeDTORequest.setCook_time(15);
        recipeDTORequest.setPrep_time(20);
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
    void shouldReturnRecipeNotFoundInIndex() {

        when(this.recipeServiceCache.getRecipeFromCache()).thenReturn(Collections.emptyList());
        when(this.recipeRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> recipe = this.recipeService.index();

        assertThat(recipe.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(recipe.getBody()).isEqualTo("recipes not found");

    }

    @Test
    void shouldReturnRecipeInIndex(){

        RecipeDTOResponse recipeDTOResponse = new RecipeDTOResponse();
        recipeDTOResponse.setTitle("category 1");
        recipeDTOResponse.setImage("image.png");
        recipeDTOResponse.setCook_time(15);
        recipeDTOResponse.setPrep_time(20);

        when(this.recipeServiceCache.getRecipeFromCache()).thenReturn(List.of(recipeDTOResponse));

        ResponseEntity<?> recipe = this.recipeService.index();
        assertThat(recipe.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(recipe.getBody()).isEqualTo(List.of(recipeDTOResponse));

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
    void shouldReturnDuplicateKeyInDB() {

        doThrow(DataIntegrityViolationException.class).when(this.recipeRepository).save(any());

        when(this.categoryRepository.findById(any())).thenReturn(Optional.of(category()));
        when(this.userClient.verifierUser(anyString())).thenReturn(true);

        CompletableFuture<ResponseEntity<String>> recipe = this.recipeService.store(createNewRecipeDTO());
        ResponseEntity<String> result = recipe.join();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(this.recipeRepository, times(1)).save(any());
    }

    @Test
    void shouldReturnNullPointerException(){

        doThrow(NullPointerException.class).when(this.recipeRepository).save(any());

        when(this.categoryRepository.findById(any())).thenReturn(Optional.of(category()));
        when(this.userClient.verifierUser(anyString())).thenReturn(true);

        RecipeDTORequest recipeDTORequest = new RecipeDTORequest();
        recipeDTORequest.setImage(null);
        recipeDTORequest.setUser("42a20af9-c378-4429-bae6-2ac79188b355");
        recipeDTORequest.setCategory_id(categoryId);

        CompletableFuture<ResponseEntity<String>> recipe = this.recipeService.store(recipeDTORequest);
        ResponseEntity<String> result = recipe.join();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(result.getBody());

        verify(this.recipeRepository, times(1)).save(any());

    }

    @Test
    void shouldReturnRecipeNotFoundInUpdate(){

        when(this.recipeRepository.findById(any())).thenReturn(Optional.empty());

        UUID id = UUID.randomUUID();
        ResponseEntity<String> recipe = this.recipeService.update(updateRecipeDTO(), id);

        assertThat(recipe.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(recipe.getBody()).isEqualTo("recipe not found");

        verify(this.recipeRepository, times(1)).findById(any());
    }

    @Test
    void shouldReturnImageNotNullUpdate(){

        RecipeDTOUpdateRequest recipeDTOUpdateRequest = new RecipeDTOUpdateRequest();
        recipeDTOUpdateRequest.setImage(uploadFile());

        Recipe recipe = new Recipe();
        recipe.setImage("https://recipesyhaddar.s3.us-east-1.amazonaws.com/recipes/1749333163374_986e8020d901fe1c313e9460495ec5c3.jpg");

        when(this.recipeRepository.findById(any())).thenReturn(Optional.of(recipe));


        when(this.s3Service.uploadFile(recipeDTOUpdateRequest.getImage(), "recipes")).thenReturn(recipe.getImage());

        this.recipeService.update(recipeDTOUpdateRequest, UUID.randomUUID());

        verify(this.s3Service).uploadFile(recipeDTOUpdateRequest.getImage(), "recipes");
        verify(this.s3Service).deleteFile(recipe.getImage());


    }

    @Test
    void shouldReturnRecipeUpdatedWhenTitleWasNull(){

        RecipeDTOUpdateRequest recipeDTOUpdateRequest = new RecipeDTOUpdateRequest();
        recipeDTOUpdateRequest.setDescription(null);

        Recipe recipe = new Recipe();
        recipe.setTitle("old title");

        when(this.recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(this.recipeRepository.save(recipe)).thenReturn(recipe);

        this.recipeService.update(recipeDTOUpdateRequest, UUID.randomUUID());

        assertEquals("old title", recipe.getTitle());
        verify(this.recipeRepository, times(1)).save(recipe);

    }

    @Test
    void shouldReturnRecipeUpdatedWhenDescriptionWasNull(){

        RecipeDTOUpdateRequest recipeDTOUpdateRequest = new RecipeDTOUpdateRequest();
        recipeDTOUpdateRequest.setDescription(null);

        Recipe recipe = new Recipe();
        recipe.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,");

        when(this.recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(this.recipeRepository.save(recipe)).thenReturn(recipe);

        this.recipeService.update(recipeDTOUpdateRequest, UUID.randomUUID());

        assertEquals("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,", recipe.getDescription());
        verify(this.recipeRepository, times(1)).save(recipe);

    }

    @Test
    void shouldReturnRecipeUpdatedWhenPrepTimeWasNull(){

        RecipeDTOUpdateRequest recipeDTOUpdateRequest = new RecipeDTOUpdateRequest();
        recipeDTOUpdateRequest.setPrep_time(null);

        Recipe recipe = new Recipe();
        recipe.setPrep_time(15);

        when(this.recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(this.recipeRepository.save(recipe)).thenReturn(recipe);

        this.recipeService.update(recipeDTOUpdateRequest, UUID.randomUUID());

        assertEquals(15, recipe.getPrep_time());

        verify(this.recipeRepository, times(1)).save(recipe);

    }

    @Test
    void shouldReturnRecipeUpdatedWhenCookTimeWasNull(){

        RecipeDTOUpdateRequest recipeDTOUpdateRequest = new RecipeDTOUpdateRequest();
        recipeDTOUpdateRequest.setCook_time(null);

        Recipe recipe = new Recipe();
        recipe.setCook_time(15);

        when(this.recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(this.recipeRepository.save(recipe)).thenReturn(recipe);

        this.recipeService.update(recipeDTOUpdateRequest, UUID.randomUUID());

        assertEquals(15, recipe.getCook_time());
        verify(this.recipeRepository, times(1)).save(recipe);

    }

    @Test
    void shouldReturnRecipeNotExistInDelete(){

        when(this.recipeRepository.existsById(any())).thenReturn(false);

        ResponseEntity<String> recipe = this.recipeService.delete(UUID.randomUUID());

        assertThat(recipe.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(recipe.getBody()).isEqualTo("recipe not found");

        verify(this.recipeRepository, never()).existsById(UUID.randomUUID());

    }

    @Test
    void shouldReturnRecipeWasDeleted(){

        when(this.recipeRepository.existsById(any())).thenReturn(true);

        Recipe recipe = new Recipe();
        when(this.recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        ResponseEntity<String> deleteRecipe = this.recipeService.delete(any());
        assertThat(deleteRecipe.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(deleteRecipe.getBody()).isEqualTo("this recipe was removed");

        verify(this.recipeRepository, times(1)).deleteById(any());

    }

    @Test
    void shouldReturnRecipeNotFoundByTitle(){

        Recipe recipe = new Recipe();
        recipe.setTitle("title");

        when(this.recipeRepository.searchByTitle(anyString())).thenReturn(List.of(recipe));
        when(this.recipeService.search("title")).thenThrow(new RuntimeException());

        ResponseEntity<?> searchRecipe = this.recipeService.search("title");
        assertThat(searchRecipe.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    void shouldReturnNoRecipeFoundById(){

        UUID id = UUID.randomUUID();

        when(this.recipeRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> recipe = this.recipeService.show(id);
        assertThat(recipe.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(recipe.getBody()).isEqualTo("recipe not found");

        verify(this.recipeRepository, never()).findById(id);

    }

    @AfterEach
    void tearAll(){
        this.categoryRepository.deleteAll();
        this.recipeRepository.deleteAll();
    }
}