package com.recipes.recipe.Service;

import com.recipes.recipe.DTO.RecipeDTO.RecipeDTORequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOResponse;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOUpdateRequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeShowDTOResponse;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Model.Entity.Recipe;
import com.recipes.recipe.Model.Entity.User;
import com.recipes.recipe.Repository.CategoryRepository;
import com.recipes.recipe.Repository.RecipeRepository;
import com.recipes.recipe.Repository.UserRepository;
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
    private UserRepository userRepository;

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
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }


    }

    @Async
    public CompletableFuture<ResponseEntity<String>> store(RecipeDTORequest recipeDTORequest) {

        try {

            MultipartFile image = recipeDTORequest.getImage();
            String file_name = this.s3Service.uploadFile(image, "recipes");

            Category category = this.categoryRepository.findById(recipeDTORequest.getCategory_id()).orElseThrow(() -> new Exception("no category found"));
            User user = this.userRepository.findById(recipeDTORequest.getUser()).orElseThrow(() -> new Exception("no user found"));

            Recipe recipe = new Recipe();
            recipe.setTitle(recipeDTORequest.getTitle());
            recipe.setImage(file_name);
            recipe.setDescription(recipeDTORequest.getDescription());
            recipe.setCategory(category);
            recipe.setPrep_time(Integer.valueOf(recipeDTORequest.getPrep_time()));
            recipe.setCook_time(Integer.valueOf(recipeDTORequest.getCook_time()));
            recipe.setUser(user);

            try {
                this.recipeRepository.save(recipe);
                this.recipeRepository.flush();
                log.info("the recipe : {} created", recipe.getTitle());
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body("the recipe was created"));

            }catch(DataIntegrityViolationException e){
                log.warn("failed to created this recipe, {}", e.getMessage());
                return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
            }

        }catch(Exception e){
            log.error("error, ", e);
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
        }

    }

    public ResponseEntity<String> update(@Valid RecipeDTOUpdateRequest recipeDTOUpdateRequest, UUID id) {
        try {

            try {

                Recipe recipe = this.recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("no recipe found"));

                MultipartFile image = null;
                String file_name = null;
                if(recipeDTOUpdateRequest.getImage() != null){
                    image = recipeDTOUpdateRequest.getImage();
                    file_name = this.s3Service.uploadFile(image, "recipes");
                    this.s3Service.deleteFile(recipe.getImage());
                }

                if(recipeDTOUpdateRequest.getTitle() != null) recipe.setTitle(recipeDTOUpdateRequest.getTitle());
                if(recipeDTOUpdateRequest.getDescription() != null) recipe.setDescription(recipeDTOUpdateRequest.getDescription());
                if(recipeDTOUpdateRequest.getPrep_time() != null) recipe.setPrep_time(Integer.valueOf(recipeDTOUpdateRequest.getPrep_time()));
                if(recipeDTOUpdateRequest.getCook_time() != null) recipe.setCook_time(Integer.valueOf(recipeDTOUpdateRequest.getCook_time()));
                if(recipeDTOUpdateRequest.getImage() != null) recipe.setImage(file_name);

                this.recipeRepository.save(recipe);
                log.info("this recipe with Id : {} was updated", id);
                return ResponseEntity.accepted().body("this recipe was updated");

            }catch(RuntimeException e){
                log.warn("error : ", e);
                return ResponseEntity.notFound().build();
            }

        }catch(Exception e){
            log.error("failed to update this recipe, ", e);
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    public ResponseEntity<String> delete(UUID id) {
        try {

          try {
              if(this.recipeRepository.existsById(id)){
                  Optional<Recipe> recipe = this.recipeRepository.findById(id);
                    this.recipeRepository.deleteById(id);

                  recipe.ifPresent(r -> {
                      String image = r.getImage();
                      this.s3Service.deleteFile(image);
                  });

                    log.info("the recipe with Id : {} was removed", id);
                    return ResponseEntity.accepted().body("this category was removed");
              }
              else
                  throw new RuntimeException("no recipe found");
          }catch(RuntimeException e){
              log.error(e.getMessage());
              return ResponseEntity.notFound().build();
          }

        }catch(Exception e){
            log.error("failed to remove this recipe with Id : {}, ", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> search(String q) {
        try {

           try{
               List<Recipe> recipes = this.recipeRepository.searchByTitleOrDescription(q);

               if(!recipes.isEmpty()){
                   List<RecipeDTOResponse> recipeDTOResponse = recipes.stream().map(RecipeDTOResponse::EntityToJson).toList();
                   return ResponseEntity.ok().body(recipeDTOResponse);
               }else
                   throw new RuntimeException("no recipe found");
           }catch (RuntimeException e){
               log.warn(e.getMessage());
               return ResponseEntity.noContent().build();
           }

        }catch(Exception e){
            log.error("Failed to find this recipe : , ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> show(UUID id) {
        try {

            try {
                Recipe recipe = this.recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("recipe not found"));

                RecipeShowDTOResponse recipeDTO = RecipeShowDTOResponse.EntityToJson(recipe);


                log.info("{}", recipe);
                return ResponseEntity.ok().body(recipeDTO);

            }catch (RuntimeException e){
                log.warn("the recipe with Id : {} not found", id);
                return ResponseEntity.notFound().build();
            }

        }catch(Exception e){
            log.error("failed to find this recipe with Id {}, ", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}