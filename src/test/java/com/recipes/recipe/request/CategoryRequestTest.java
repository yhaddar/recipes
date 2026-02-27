package com.recipes.recipe.request;

import com.recipes.recipe.exception.HandlerValidationException;
import jakarta.validation.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRequestTest {
    List<String> extensions = List.of("jpg", "jpeg", "png");
    @Test
    @DisplayName("method for test the message of null for title")
    void testCategoryRequestNullForTitle() throws HandlerValidationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image".getBytes()
        );

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setImage(file);
        categoryRequest.setTitle(null);
        Set<ConstraintViolation<CategoryRequest>> validation = validator.validate(categoryRequest);

        assertFalse(validation.isEmpty());
        assertEquals("category title is required", validation.iterator().next().getMessage());

    }

    @Test
    @DisplayName("method for test the size of title max 20")
    void testCategoryRequestSizeOfTitleMax20(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image".getBytes()
        );
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setImage(file);
        categoryRequest.setTitle("this title is twenty seven ch");

        Set<ConstraintViolation<CategoryRequest>> validation = validator.validate(categoryRequest);
        assertEquals(29, categoryRequest.getTitle().length());
        assertEquals("category title cannot exceed 20 characters", validation.iterator().next().getMessage());
    }

    @Test
    @DisplayName("method for test the pattern of title")
    void testCategoryRequestTitlePattern(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image".getBytes()
        );

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setImage(file);

        String[] validates = {"_recipe", "A.", " Category.4", "Category@Home,", "Recipe!"};

        for(String validate: validates){
            categoryRequest.setTitle(validate);
            Set<ConstraintViolation<CategoryRequest>> violation = validator.validate(categoryRequest);
            assertFalse(violation.isEmpty(), "Expected validation error for title: " + validate);
            ConstraintViolation<CategoryRequest> violationItem = violation.iterator().next();
            assertEquals("category title contains invalid characters or starts with a space.", violationItem.getMessage());
        }
    }

    @Test
    @DisplayName("method for test the error when upload a invalid image")
    void testCategoryRequestErrorWhenUploadInvalidImage(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "image",
                "image.gif",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "image".getBytes()
        );

        String filename = mockMultipartFile.getOriginalFilename();
        String[] extension = filename.split("\\.");

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("breakfast");
        categoryRequest.setImage(mockMultipartFile);

        Set<ConstraintViolation<CategoryRequest>> validation = validator.validate(categoryRequest);
        assertEquals("image invalid, use image .jpg, .png or .jpeg", validation.iterator().next().getMessage());
        assertFalse(this.extensions.contains(extension[1]));
    }

    @Test
    @DisplayName("method for test if the image is null")
    void testCategoryRequestImageIsNull(){
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("breakfast");
        categoryRequest.setImage(null);

        assertTrue(categoryRequest.isImageNull());
    }
}