package com.recipes.recipe.Controller;

import com.recipes.recipe.DTO.CategoryDTO.CategoryDTORequest;
import com.recipes.recipe.DTO.CategoryDTO.CategoryUpdateRequest;
import com.recipes.recipe.Service.CategoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<?> index(){
        return this.categoryService.index();
    }

    @PostMapping("/add")
    public ResponseEntity<String> store(@ModelAttribute @Valid CategoryDTORequest categoryDTORequest){
        return this.categoryService.store(categoryDTORequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@PathParam("id") UUID id){
        return this.categoryService.delete(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute @Valid CategoryUpdateRequest categoryUpdateRequest, @PathParam("id") UUID id){
        return this.categoryService.update(categoryUpdateRequest, id);
    }
}