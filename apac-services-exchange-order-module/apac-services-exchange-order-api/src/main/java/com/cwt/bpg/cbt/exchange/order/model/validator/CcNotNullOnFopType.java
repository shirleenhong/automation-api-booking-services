package com.cwt.bpg.cbt.exchange.order.model.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;

/**
 * Validates that field {@code dependentFields} is not null if
 * field {@code fieldName} has value {@code fopTypes}.
 **/

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface CcNotNullOnFopType {
    String[] fopTypes();

    String message() default "{CcNotNullOnFopType.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
