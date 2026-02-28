package com.recipes.recipe.controllers;

import com.recipes.recipe.dto.CategoryDTO;
import com.recipes.recipe.request.CategoryRequest;
import com.recipes.recipe.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> index(){
        return this.categoryService.index();
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<String>> store(@Valid CategoryRequest categoryRequest) throws IOException {
        return this.categoryService.store(categoryRequest);
    }

    @PutMapping("/update")
    public CompletableFuture<ResponseEntity<String>> update(@PathParam("id") UUID id, @ModelAttribute CategoryRequest categoryRequest) throws  IOException{
        return this.categoryService.update(id, categoryRequest);
    }

    @DeleteMapping("/delete")
    public CompletableFuture<ResponseEntity<String>> delete(@PathParam("id") UUID id) {
        return this.categoryService.delete(id);
    }

}
