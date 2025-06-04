package com.recipes.recipe.Annotation;

import com.recipes.recipe.Validation.UserExistValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistValidation.class)
public @interface UserExist {
    public String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}