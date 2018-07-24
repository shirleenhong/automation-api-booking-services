package com.cwt.bpg.cbt.exchange.order.model.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Implementation of {@link ValidateOnInsert} validator.
 **/
public class ValidateOnInsertValidator implements ConstraintValidator<ValidateOnInsert, Object> {
	private String fieldName;
	private String[] dependentFields;

	@Override
	public void initialize(ValidateOnInsert annotation) {
		fieldName = "eoNumber";
        dependentFields = annotation.dependentFields();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {

		if (value == null) {
			return true;
		}

		try {
			String fieldValue = BeanUtils.getProperty(value, fieldName);


			for(String dependFieldName : dependentFields){


				Object dependFieldValue = PropertyUtils.getProperty(value, dependFieldName);
//
//                Field field = value.getClass().getDeclaredField(dependFieldName);
//                field.setAccessible(true);
//                Object dependentObject = field.get(value);

                if (fieldValue == null && dependFieldValue != null) {

					ctx.disableDefaultConstraintViolation();
					ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
							.addNode(dependFieldName)
							.addConstraintViolation();

					Set<ConstraintViolation<Object>> errors = Validation.buildDefaultValidatorFactory().getValidator()
							.validate((dependFieldValue));

                    if (!errors.isEmpty())
                    	throw new IllegalArgumentException(dependFieldName + " incomplete or invalid");
                }

            }
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
		return true;
	}

}
