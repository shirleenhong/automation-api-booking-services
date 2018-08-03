package com.cwt.bpg.cbt.exchange.order.model.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidateOnInsertValidator.class)
@Documented
public @interface ValidateOnInsert {
	String[] dependentFields();

	String message() default "{ValidateOnInsert.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
