package com.recipes.authentication.Validation;

import com.recipes.authentication.Annotation.UserExist;
import com.recipes.authentication.Repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

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