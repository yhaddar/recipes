package com.recipes.recipe.Service;

import com.recipes.recipe.Client.UserClient;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTORequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOResponse;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOUpdateRequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeShowDTOResponse;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Model.Entity.Recipe;
import com.recipes.recipe.Repository.CategoryRepository;
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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserClient userClient;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private RecipeServiceCache recipeServiceCache;
    private final Logger log = LoggerFactory.getLogger(RecipeService.class);

    public ResponseEntity<?> index() {

        try {
            List<RecipeDTOResponse> recipeDTOResponses = this.recipeServiceCache.getRecipeFromCache();

            if (recipeDTOResponses.isEmpty()) {

                throw new RuntimeException("recipes not found");

            } else {
                return ResponseEntity.ok().body(recipeDTOResponses);
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }


    }

    @Async
    public CompletableFuture<ResponseEntity<String>> store(RecipeDTORequest recipeDTORequest) {


        try {
            try {

                MultipartFile image = recipeDTORequest.getImage();
                String file_name = this.s3Service.uploadFile(image, "recipes");

                Category category = this.categoryRepository.findById(recipeDTORequest.getCategory_id()).orElseThrow(() -> new RuntimeException("no category found"));

                Recipe recipe = new Recipe();
                recipe.setTitle(recipeDTORequest.getTitle());
                recipe.setImage(file_name);
                recipe.setDescription(recipeDTORequest.getDescription());
                recipe.setCategory(category);
                recipe.setPrep_time(recipeDTORequest.getPrep_time());
                recipe.setCook_time(recipeDTORequest.getCook_time());

                Boolean verifierUser = this.userClient.verifierUser(recipeDTORequest.getUser());

                if(!verifierUser)
                    throw new RuntimeException("user don't exists");
                else {

                    recipe.setUser(recipeDTORequest.getUser());

                    try {
                        this.recipeRepository.save(recipe);
                        log.info("the recipe : {} created", recipe.getTitle());
                        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body("the recipe was created"));

                    } catch (DataIntegrityViolationException e) {
                        log.warn("failed to create this recipe.");
                        return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
                    }
                }

            } catch (RuntimeException e) {
                log.error("error, ", e);
                return CompletableFuture.completedFuture(ResponseEntity.ok().body(e.getMessage()));

            }

        } catch (NullPointerException e) {
            log.error("NullPointerException, {}", e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.ok().body("NullPointerException " + e.getMessage()));
        }

    }

    public ResponseEntity<String> update(@Valid RecipeDTOUpdateRequest recipeDTOUpdateRequest, UUID id) {
        try {

            try {

                Recipe recipe = this.recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("recipe not found"));

                MultipartFile image = null;
                String file_name = null;
                if (recipeDTOUpdateRequest.getImage() != null) {
                    image = recipeDTOUpdateRequest.getImage();
                    file_name = this.s3Service.uploadFile(image, "recipes");
                    this.s3Service.deleteFile(recipe.getImage());
                }

                if (recipeDTOUpdateRequest.getTitle() != null) recipe.setTitle(recipeDTOUpdateRequest.getTitle());
                if (recipeDTOUpdateRequest.getDescription() != null)
                    recipe.setDescription(recipeDTOUpdateRequest.getDescription());
                if (recipeDTOUpdateRequest.getPrep_time() != null)
                    recipe.setPrep_time(recipeDTOUpdateRequest.getPrep_time());
                if (recipeDTOUpdateRequest.getCook_time() != null)
                    recipe.setCook_time(recipeDTOUpdateRequest.getCook_time());
                if (recipeDTOUpdateRequest.getImage() != null) recipe.setImage(file_name);

                this.recipeRepository.save(recipe);
                log.info("this recipe with Id : {} was updated", id);
                return ResponseEntity.accepted().body("this recipe was updated");

            } catch (RuntimeException e) {
                log.warn("error : ", e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

        } catch (Exception e) {
            log.error("failed to update this recipe, ", e);
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    public ResponseEntity<String> delete(UUID id) {
        try {

            try {
                if (this.recipeRepository.existsById(id)) {
                    Optional<Recipe> recipe = this.recipeRepository.findById(id);
                    this.recipeRepository.deleteById(id);

                    recipe.ifPresent(r -> {
                        String image = r.getImage();
                        this.s3Service.deleteFile(image);
                    });

                    log.info("the recipe with Id : {} was removed", id);
                    return ResponseEntity.accepted().body("this recipe was removed");
                } else
                    throw new RuntimeException("recipe not found");
            } catch (RuntimeException e) {
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

        } catch (Exception e) {
            log.error("failed to remove this recipe with Id : {}, ", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> search(String q) {
        try {

            try {

                List<RecipeDTOResponse> recipeDTOResponse = this.recipeServiceCache.getRecipeSearchFromCache(q);
                return ResponseEntity.ok().body(recipeDTOResponse);

            } catch (RuntimeException e) {
                log.warn(e.getMessage());
                return ResponseEntity.noContent().build();
            }

        } catch (Exception e) {
            log.error("Failed to find this recipe : , ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> show(UUID id) {
        try {

            try {


                RecipeShowDTOResponse recipeDTO = this.recipeServiceCache.getRecipeDetailFromCache(id);


                if(recipeDTO != null){
                    log.info("{}", recipeDTO);
                    return ResponseEntity.ok().body(recipeDTO);
                }else
                    throw new RuntimeException("recipe not found");

            } catch (RuntimeException e) {
                log.warn("the recipe with Id : {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

        } catch (Exception e) {
            log.error("failed to find this recipe with Id {}, ", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}