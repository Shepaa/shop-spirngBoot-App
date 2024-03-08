package com.Intent.shop.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DescriptionWordCountValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionWordCount {
    String message() default "Description must contain at most {value} words";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value() default 5;
}
