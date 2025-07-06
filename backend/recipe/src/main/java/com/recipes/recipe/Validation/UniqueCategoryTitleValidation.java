package com.recipes.recipe.Validation;

import com.recipes.recipe.Annotation.Unique;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Repository.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UniqueCategoryTitleValidation implements ConstraintValidator<Unique, String> {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isValid(String t, ConstraintValidatorContext constraintValidatorContext) {

        Optional<Category> category = this.categoryRepository.findByTitle(t);

        return category.isEmpty();
    }
}