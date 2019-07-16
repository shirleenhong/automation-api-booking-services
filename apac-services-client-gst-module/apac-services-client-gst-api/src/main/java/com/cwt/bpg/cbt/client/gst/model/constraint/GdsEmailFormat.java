package com.cwt.bpg.cbt.client.gst.model.constraint;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;


@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { GdsEmailValidator.class })
@Documented
@ReportAsSingleViolation
public @interface GdsEmailFormat
{
    String message() default "GDS Email is not valid or more than 35 chars after transformation";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
