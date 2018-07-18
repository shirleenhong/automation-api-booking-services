package com.cwt.bpg.cbt.exchange.order.model.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Implementation of {@link NotEmptyIfAnotherFieldNull} validator.
 **/
public class NotEmptyIfAnotherFieldNullValidator implements ConstraintValidator<NotEmptyIfAnotherFieldNull, Object> {

	private String fieldName;
	private String dependFieldName;

	@Override
	public void initialize(NotEmptyIfAnotherFieldNull annotation) {
		fieldName = annotation.fieldName();
		dependFieldName = annotation.dependFieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {

		if (value == null) {
			return true;
		}

		try {
			String fieldValue = BeanUtils.getProperty(value, fieldName);
			String dependFieldValue = BeanUtils.getProperty(value, dependFieldName);

			if ((fieldValue == null && (dependFieldValue == null || dependFieldValue.isEmpty()))
					|| dependFieldValue != null && dependFieldValue.isEmpty()) {
				ctx.disableDefaultConstraintViolation();
				ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
						.addNode(dependFieldName).addConstraintViolation();
				throw new IllegalArgumentException("["+dependFieldName+"] should not be empty");
			}

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}

		return true;
	}
}
