package com.recipes.recipe.Controller;

import com.recipes.recipe.DTO.RecipeDTO.RecipeDTORequest;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOResponse;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOUpdateRequest;
import com.recipes.recipe.Service.RecipeService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {

    @Autowired
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public ResponseEntity<?> index(){
        return this.recipeService.index();
    }

    @PostMapping("/add")
    public CompletableFuture<ResponseEntity<String>> store(@ModelAttribute @Valid RecipeDTORequest recipeDTORequest){
        return this.recipeService.store(recipeDTORequest);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@ModelAttribute @Valid RecipeDTOUpdateRequest recipeDTOUpdateRequest, @PathParam("id") UUID id){
        return this.recipeService.update(recipeDTOUpdateRequest, id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@PathParam("id") UUID id){
        return this.recipeService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@PathParam("q") String q){
        return this.recipeService.search(q);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> show(@PathParam("id") UUID id){
        return this.recipeService.show(id);
    }

}