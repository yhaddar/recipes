package com.recipes.recipe.services;

import com.recipes.recipe.config.S3Config;
import com.recipes.recipe.dto.RecipeDTO;
import com.recipes.recipe.exception.HandlerValidationException;
import com.recipes.recipe.exception.NotFoundException;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.models.Recipe;
import com.recipes.recipe.models.User;
import com.recipes.recipe.repositories.CategoryRepository;
import com.recipes.recipe.repositories.RecipeRepository;
import com.recipes.recipe.repositories.UserRepository;
import com.recipes.recipe.request.RecipeRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final S3Config s3Config;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    RecipeService(RecipeRepository recipeRepository, S3Config s3Config, UserRepository userRepository, CategoryRepository categoryRepository){
        this.recipeRepository = recipeRepository;
        this.s3Config = s3Config;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(rollbackOn = NotFoundException.class)
    public List<RecipeDTO> index() {
        List<Recipe> recipes = this.recipeRepository.findAll();

        if(recipes.isEmpty()){
            throw new RuntimeException("Recipe not found");
        }else {
            return recipes.stream().map(RecipeDTO::toJSON).toList();
        }
    }

    @Async
    @Transactional(rollbackOn = RuntimeException.class)
    public CompletableFuture<ResponseEntity<String>> store(@Valid RecipeRequest request) throws IOException {

        String file = request.getMediaUrl().getOriginalFilename();
        String key = "recipes/"+file;
        this.s3Config.uploadFile(key, request.getMediaUrl().getInputStream());

        User user = this.userRepository.findById(request.getUser()).orElseThrow(() -> new RuntimeException("user not found"));
        Category category = this.categoryRepository.findById(request.getCategory()).orElseThrow(() -> new RuntimeException("category not found"));

        Recipe recipe = new Recipe();
        recipe.setRecipe_title(request.getRecipeTitle());
        recipe.setCategory(category);
        recipe.setType(request.getType());
        recipe.setDescription(request.getDescription());
        recipe.setCooking_time(request.getCookingTime());
        recipe.setCountry_origin(request.getCountryOrigin());
        recipe.setDifficulty(request.getDifficulty());
        recipe.setUser(user);
        recipe.setMedia_url(this.s3Config.getUrl(key));

        this.recipeRepository.save(recipe);

        System.out.println(request.getType());

        return CompletableFuture.completedFuture(ResponseEntity.ok().body("your recipe has been stored"));
    }
}