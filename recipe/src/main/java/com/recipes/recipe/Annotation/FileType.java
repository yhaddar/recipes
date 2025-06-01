package com.recipes.recipe.Annotation;

import com.recipes.recipe.Validation.FileTypeValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeValidation.class)
public @interface FileType {

    public String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}