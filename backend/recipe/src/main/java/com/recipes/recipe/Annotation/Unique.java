package com.recipes.recipe.Annotation;

import com.recipes.recipe.Validation.UniqueCategoryTitleValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueCategoryTitleValidation.class })
public @interface Unique {

    public String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}