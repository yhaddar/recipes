package com.recipes.recipe.DTO.RecipeDTO;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class RecipeDTORequestTest {

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

    private MockMultipartFile uploadFile(){
        return new MockMultipartFile(
                "image",
                "image.png",
                "image/pdf",
                "hello word".getBytes()
        );
    }

    @Test
    void shouldReturnTitleWasEmpty(){
        this.recipeDTORequest.setTitle("");

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);

        Boolean messageValidation = messageValidation("title", "the title was not be empty", validators);
        assertFalse(validators.isEmpty());
        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnTitleWasNull(){
        this.recipeDTORequest.setTitle(null);

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);

        Boolean messageValidation = messageValidation("title", "the title was not be null", validators);
        assertTrue(messageValidation);
    }


    @Test
    void shouldReturnTitleWasBlank(){
        this.recipeDTORequest.setTitle(" ");

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);

        Boolean messageValidation = messageValidation("title", "the title was not be blank", validators);
        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnTitleWasInvalid(){
        this.recipeDTORequest.setTitle("hello@world!");

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("title", "this title invalid", validators);
        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnImageWasNull(){
        this.recipeDTORequest.setImage(null);

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("image", "the image was not be null", validators);

        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnImageInvalid(){

        MockMultipartFile uploadFile = uploadFile();
        this.recipeDTORequest.setImage(uploadFile);

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("image", "the type of image invalid, import image with png or jpg or jpeg or webp", validators);

        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnBlankInDescription(){
        this.recipeDTORequest.setDescription("");

        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("description", "the description was not be blank", validators);

        assertTrue(messageValidation);
    }

    @Test
    void shouldReturnNullInDescription(){

        this.recipeDTORequest.setDescription(null);

        Set<ConstraintViolation<RecipeDTORequest>> violations = validator.validate(this.recipeDTORequest);

        Boolean messageValidation = messageValidation("description", "the description was not be null", violations);

        assertTrue(messageValidation);

    }

    @Test
    void shouldReturnEmptyInDescription(){
        this.recipeDTORequest.setDescription("");

        Set<ConstraintViolation<RecipeDTORequest>> violations = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("description", "the description was not be empty", violations);

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
    void shouldReturnNullInPrepTime(){
        this.recipeDTORequest.setPrep_time(null);
        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("prep_time",  "the preparation time was not be null", validators);
        assertTrue(messageValidation);

    }

    @Test
    void shouldReturnPrepTimeMinOf1(){
        this.recipeDTORequest.setPrep_time(-100);
        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        assertTrue(messageValidation("prep_time", "the preparation time was be au min 1",validators));
    }

    @Test
    void shouldReturnNullInCookTime(){
        this.recipeDTORequest.setCook_time(null);
        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        Boolean messageValidation = messageValidation("cook_time",  "the cooking time was not be null", validators);
        assertTrue(messageValidation);

    }

    @Test
    void shouldReturnCookTimeMinOf1(){
        this.recipeDTORequest.setCook_time(-100);
        Set<ConstraintViolation<RecipeDTORequest>> validators = validator.validate(this.recipeDTORequest);
        assertTrue(messageValidation("cook_time", "the cooking time was be au min 1",validators));
    }


}