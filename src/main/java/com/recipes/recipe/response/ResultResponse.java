package com.recipes.recipe.response;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResultResponse {
    private ResultResponse(){
        throw new UnsupportedOperationException("ResultResponse can't be instantiated");
    }
    public static <T> ResponseEntity<T> success(@Nullable T object, HttpStatus status){
        return ResponseEntity.status(status).body(object);
    }
}
