package com.recipes.recipe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {
    @GetMapping("")
    String index(){
        return "hello world";
    }
}
