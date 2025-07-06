package com.recipes.recipe.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerValidationDataIntegrity {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> getMessage(DataIntegrityViolationException e){

        Map<String, String> errors = new HashMap<>();

        errors.put("message :", e.getMessage());
        return errors;
    }
}