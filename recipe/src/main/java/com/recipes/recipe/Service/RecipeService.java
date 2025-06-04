package com.recipes.recipe.Service;

import com.recipes.recipe.DTO.RecipeDTO.RecipeDTORequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOResponse;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOUpdateRequest;
import com.recipes.recipe.Model.Entity.Recipe;
import com.recipes.recipe.Repository.RecipeRepository;
import com.recipes.recipe.Service.UploadToS3.S3Service;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private S3Service s3Service;
    private final Logger log = LoggerFactory.getLogger(RecipeService.class);
    public ResponseEntity<?> index() {

        try {
            List<Recipe> recipes = this.recipeRepository.findAll();
            List<RecipeDTOResponse> recipeDTOResponses = recipes.stream().map(RecipeDTOResponse::EntityToJson).toList();
            if(!recipeDTOResponses.isEmpty()){

                return ResponseEntity.ok().body(recipeDTOResponses);

            }else {
                throw new RuntimeException("no recipes found");
            }
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }


    }

    @Async
    public CompletableFuture<ResponseEntity<String>> store(@Valid RecipeDTORequest recipeDTORequest) {

        try {

            MultipartFile image = recipeDTORequest.getImage();
            String file_name = this.s3Service.uploadFile(image, "recipes");


            Recipe recipe = new Recipe();
            recipe.setTitle(recipeDTORequest.getTitle());
            recipe.setImage(file_name);
            recipe.setDescription(recipeDTORequest.getDescription());
            recipe.setCategory(recipeDTORequest.getCategory());
            recipe.setPrep_time(Integer.valueOf(recipeDTORequest.getPrep_time()));
            recipe.setCook_time(Integer.valueOf(recipeDTORequest.getCook_time()));
            recipe.setUser(recipeDTORequest.getUser());

            try {
                this.recipeRepository.save(recipe);
                log.info("the recipe : {} created", recipe.getTitle());
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body("the recipe was craeted"));

            }catch(DataIntegrityViolationException e){
                log.warn("failed to created this recipe, {}", e.getMessage());
                return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
            }

        }catch(Exception e){
            log.error("error, {}", e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
        }

    }

    public ResponseEntity<String> update(@Valid RecipeDTOUpdateRequest recipeDTOUpdateRequest, UUID id) {
        return null;
    }

    public ResponseEntity<String> delete(UUID id) {
        return null;
    }

    public ResponseEntity<?> search(String q) {
        return null;
    }

    public ResponseEntity<RecipeDTOResponse> show(UUID id) {
        return null;
    }
}