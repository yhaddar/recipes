package com.recipes.recipe.controllers;

import com.recipes.recipe.dto.RecipeDTO;
import com.recipes.recipe.request.RecipeRequest;
import com.recipes.recipe.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping("")
    List<RecipeDTO> index(){
        return this.recipeService.index();
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CompletableFuture<ResponseEntity<String>> store(@Valid RecipeRequest recipeRequest) throws IOException {
        return this.recipeService.store(recipeRequest);
    }
}
