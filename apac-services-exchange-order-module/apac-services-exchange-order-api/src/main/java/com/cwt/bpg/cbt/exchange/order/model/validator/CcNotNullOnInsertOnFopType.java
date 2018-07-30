package com.cwt.bpg.cbt.exchange.order.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates that field {@code dependentFields} is not null if
 * field {@code fieldName} has value {@code fopTypes}.
 **/

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CcNotNullOnInsertOnFopTypeValidator.class)
@Documented
public @interface CcNotNullOnInsertOnFopType {
    String[] fopTypes();

    String message() default "{CcNotNullOnInsertOnFopType.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CcNotNullOnInsertOnFopType[] value();
    }

}
