package com.cwt.bpg.cbt.exchange.order.model.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validates that field {@code dependFieldName} is not null if
 * field {@code fieldName} has value {@code fieldValue}.
 **/

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullOnInsertValidator.class)
@Documented
public @interface NotNullOnInsert {
    String dependFieldName();

    String message() default "{NotNullOnInsert.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNullOnInsert[] value();
    }

}


//    final String fieldValue       = BeanUtils.getProperty(value, fieldName);
//    final Object dependFieldValue = PropertyUtils.getProperty(value, dependFieldName);
//
//        if (expectedFieldValue.equals(fieldValue)) {
//
//                ctx.disableDefaultConstraintViolation();
//                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//                Validator validator = factory.getValidator();
//
//                Set<ConstraintViolation<Object>> errorList = validator.validate(dependFieldValue);
//
//        for(ConstraintViolation<Object> error : errorList) {
//
//        ctx.buildConstraintViolationWithTemplate(error.getMessageTemplate())
//        .addNode(dependFieldName+"."+error.getPropertyPath())
//        .addConstraintViolation();
//        }
//
//        return errorList.isEmpty();
//        }