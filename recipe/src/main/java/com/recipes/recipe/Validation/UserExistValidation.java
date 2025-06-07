package com.recipes.recipe.Validation;

import com.recipes.recipe.Annotation.UserExist;
import com.recipes.recipe.Model.Entity.User;
import com.recipes.recipe.Repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class UserExistValidation implements ConstraintValidator<UserExist, UUID> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UUID u, ConstraintValidatorContext constraintValidatorContext) {
        if(u == null)
            return false;

        return this.userRepository.findById(u).isPresent();
    }
}