package com.recipes.recipe.controllers;

import com.recipes.recipe.dto.CategoryDTO;
import com.recipes.recipe.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService = new CategoryService();

    @GetMapping
    public List<CategoryDTO> index(){
        return this.categoryService.index();
    }

    @PostMapping("/add")
    public String store(){
        return "hello world";
    }

    @PutMapping("/update")
    public String update(){
        return "hello world";
    }

    @DeleteMapping
    public String delete(){
        return "hello world";
    }

}
