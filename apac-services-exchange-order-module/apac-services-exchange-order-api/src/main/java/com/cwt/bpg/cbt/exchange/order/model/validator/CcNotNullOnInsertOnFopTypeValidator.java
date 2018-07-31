package com.cwt.bpg.cbt.exchange.order.model.validator;

import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.ServiceInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

/**
 * Implementation of {@link CcNotNullOnInsertOnFopType} validator.
 **/
public class CcNotNullOnInsertOnFopTypeValidator implements ConstraintValidator<CcNotNullOnInsertOnFopType, Object> {

	private String[] fopTypes;

	@Override
	public void initialize(CcNotNullOnInsertOnFopType annotation) {

		fopTypes = annotation.fopTypes();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {

		if (value == null) {
			return true;
		}

		try {
			String eoNumberValue = BeanUtils.getProperty(value, "eoNumber");
			if (eoNumberValue == null) {
				ServiceInfo serviceInfo = (ServiceInfo) PropertyUtils.getProperty(value, "serviceInfo");

				if (serviceInfo != null && serviceInfo.getFormOfPayment() != null
						&& serviceInfo.getFormOfPayment().getFopType() != null) {
					String fopTypeVal = serviceInfo.getFormOfPayment().getFopType().getCode();
					CreditCard creditCard = serviceInfo.getFormOfPayment().getCreditCard();

					for (String fopType : fopTypes) {
						if (fopTypeVal.equals(fopType) && creditCard == null) {
							ctx.disableDefaultConstraintViolation();
							ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
									.addNode("creditCard").addConstraintViolation();
							throw new IllegalArgumentException(
									"[creditCard] should not be null if [fopType] is [" + fopType + "].");
						}
					}

				}
			}



		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}

		return true;
	}

}
