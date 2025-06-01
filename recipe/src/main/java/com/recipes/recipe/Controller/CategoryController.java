package com.recipes.recipe.Controller;

import com.recipes.recipe.DTO.CategoryDTO;
import com.recipes.recipe.Service.CategoryService;
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
    public ResponseEntity<String> store(@ModelAttribute @Valid CategoryDTO categoryDTO){
        return this.categoryService.store(categoryDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@PathParam("id") UUID id){
        return this.categoryService.delete(id);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@ModelAttribute @Valid CategoryDTO categoryDTO, @PathParam("id") UUID id){
        return this.categoryService.update(categoryDTO, id);
    }
}