package com.recipes.recipe.request;

import com.recipes.recipe.enums.Difficulty;
import com.recipes.recipe.enums.Type;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class RecipeRequestTest {
    List<String> extensions = List.of("jpg", "jpeg", "png");

    @Test
    @DisplayName("method for test recipe title is return null")
    void testRecipeTitleNull(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", "image".getBytes());

        RecipeRequest request = new RecipeRequest();
        request.setRecipeTitle(null);
        request.setMediaUrl(file);
//        request.setCategory(new Category());
        request.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book");
        request.setDifficulty(Difficulty.MEDIUM);
        request.setType(Type.RECIPE);
        request.setCookingTime(30);
        request.setCountryOrigin("morocco");
//        request.setUser(new User());

        Set<ConstraintViolation<RecipeRequest>> validation = validator.validate(request);
        assertFalse(validation.isEmpty());
        assertEquals("recipe title is required", validation.iterator().next().getMessage());

    }

    @Test
    @DisplayName("method for test pattern of recipe title")
    void testTitlePattern(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        String[] fakeTitles = {"_ChocolateCake", "Cake!"};
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "image/png", "image".getBytes());

        RecipeRequest request = new RecipeRequest();
        request.setMediaUrl(file);
//        request.setCategory(new Category());
        request.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book");
        request.setDifficulty(Difficulty.MEDIUM);
        request.setType(Type.RECIPE);
        request.setCookingTime(30);
        request.setCountryOrigin("morocco");
//        request.setUser(new User());

        for(String title: fakeTitles){
            request.setRecipeTitle(title);

            Set<ConstraintViolation<RecipeRequest>> validation = validator.validate(request);
            assertFalse(validation.isEmpty(), "Expected validation error for title: " + title);
            ConstraintViolation<RecipeRequest> violationItem = validation.iterator().next();
            assertEquals("recipe title must contain only letters with spaces and without special characters", violationItem.getMessage());
        }

    }

}