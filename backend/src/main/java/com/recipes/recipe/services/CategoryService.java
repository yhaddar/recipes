package com.recipes.recipe.services;

import com.recipes.recipe.dto.CategoryDTO;
import com.recipes.recipe.exception.NotFoundException;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.repositories.categoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAsync
public class CategoryService {
    @Autowired
    private categoryRepository categoryRepository;

    @Transactional(rollbackOn = NotFoundException.class)
    @Cacheable(value = "CATEGORY_SERVICE", key = "'category'")
    public List<CategoryDTO> index() throws NotFoundException {
        List<Category> category = this.categoryRepository.findAll();

        if(category.isEmpty()){
            throw new RuntimeException("no category found");
        }else {
            return category.stream().map(CategoryDTO::toJSON).toList();
        }
    }

}
