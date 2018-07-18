package com.cwt.bpg.cbt.exchange.order.model.validator;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

/**
 * Implementation of {@link NotNullIfAnotherFieldHasValue} validator.
 **/
public class NotNullIfAnotherFieldHasValueValidator
        implements ConstraintValidator<NotNullIfAnotherFieldHasValue, Object> {

        private String fieldName;
        private String expectedFieldValue;
        private String dependFieldName;

        @Override
        public void initialize(NotNullIfAnotherFieldHasValue annotation) {
            fieldName          = annotation.fieldName();
            expectedFieldValue = annotation.fieldValue();
            dependFieldName    = annotation.dependFieldName();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext ctx) {

            if (value == null) {
                return true;
            }

            try {
                String fieldValue       = BeanUtils.getProperty(value, fieldName);
                String dependFieldValue = BeanUtils.getProperty(value, dependFieldName);

                if (expectedFieldValue.equals(fieldValue) && dependFieldValue == null) {
                    ctx.disableDefaultConstraintViolation();
                    ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                            .addNode(dependFieldName)
                            .addConstraintViolation();
                    throw new IllegalArgumentException("["+dependFieldName+"] should not be null if ["+fieldName+"] is ["+expectedFieldValue+"].");
                }

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }

            return true;
        }
}
