package com.recipes.recipe.Validation;

import com.recipes.recipe.Annotation.CategoryExist;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Repository.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class CategoryExistValidation implements ConstraintValidator<CategoryExist, UUID> {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public boolean isValid(UUID s, ConstraintValidatorContext constraintValidatorContext) {

        if(s  == null){
            return false;
        }

        return this.categoryRepository.findById(s).isPresent();
    }
}