package com.cwt.bpg.cbt.exchange.order.model.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.FopTypes;

/**
 * Implementation of {@link CcNotNullOnFopType} validator.
 **/
public class CcNotNullOnInsertOnFopTypeValidator implements ConstraintValidator<CcNotNullOnFopType, Object> {

	private String[] fopTypes;

	@Override
	public void initialize(CcNotNullOnFopType annotation) {
		fopTypes = annotation.fopTypes();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {

		if (value == null) {
			return true;
		}

		try {
			FopTypes fopTypeVal = (FopTypes) PropertyUtils.getProperty(value, "fopType");
			CreditCard cc = (CreditCard) PropertyUtils.getProperty(value, "creditCard");

			if (fopTypeVal != null) {
				for (String fopType : fopTypes) {
					if (fopTypeVal.getCode().equals(fopType) && cc == null) {
						throw new IllegalArgumentException(
								"[creditCard] should not be null if [fopType] is [" + fopType + "].");
					}else if(fopTypeVal.getCode().equalsIgnoreCase(FopTypes.INVOICE.getCode()) && cc!=null){
						PropertyUtils.setProperty(value,"creditCard",null);
					}
				}
			}
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}

		return true;
	}

}
