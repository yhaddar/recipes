package com.recipes.recipe.DTO.RecipeDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDTOUpdateRequestTest {

    private static Validator validator;
    private RecipeDTORequest recipeDTORequest;

    @BeforeAll
    static void setup(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp(){
        this.recipeDTORequest = new RecipeDTORequest();
    }

    private Boolean messageValidation(String type, String message, Set<ConstraintViolation<RecipeDTORequest>> validators){
        return validators.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals(type) && v.getMessage().equals(message));
    }

    @Test
    void shouldReturnTitleWasInvalid(){
        this.recipeDTORequest.setTitle("hello@world!");

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("title", "this title invalid", validators);
        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnDescriptionInvalid(){
        this.recipeDTORequest.setDescription("hello@world!#");
        Set<ConstraintViolation<RecipeDTORequest>> violations = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("description", "this description was invalid", violations);

        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnPrepTimeMinOf1(){
        this.recipeDTORequest.setPrep_time(-100);
        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        assertTrue(messageValidation("prep_time", "the preparation time was be au min 1",validators));
    }

    @Test
    void shouldReturnCookTimeMinOf1(){
        this.recipeDTORequest.setCook_time(-100);
        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        assertTrue(messageValidation("cook_time", "the cooking time was be au min 1",validators));
    }
}