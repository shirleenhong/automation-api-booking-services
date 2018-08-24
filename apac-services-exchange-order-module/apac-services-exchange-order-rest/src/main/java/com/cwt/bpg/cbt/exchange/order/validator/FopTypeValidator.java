package com.cwt.bpg.cbt.exchange.order.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FopType;

@Component
public class FopTypeValidator implements Validator<ExchangeOrder> {

	private List<String> creditCardTypes = Arrays.asList(
			FopType.CWT.getCode(),
			FopType.CREDIT_CARD.getCode());

	@Override
	public void validate(ExchangeOrder input) {
		if (creditCardTypes.contains(input.getServiceInfo().getFormOfPayment().getFopType().getCode()) && input.getServiceInfo().getFormOfPayment().getCreditCard() == null) {
			throw new IllegalArgumentException("Credit Card required for fopTypes " + input.getServiceInfo().getFormOfPayment().getFopType());
		}
	}
}
