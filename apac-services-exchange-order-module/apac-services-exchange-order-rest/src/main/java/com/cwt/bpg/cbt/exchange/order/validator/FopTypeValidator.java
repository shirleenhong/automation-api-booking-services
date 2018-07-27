package com.cwt.bpg.cbt.exchange.order.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FopTypes;

@Component
public class FopTypeValidator implements Validator<ExchangeOrder> {

	private List<String> creditCardTypes = Arrays.asList(
			FopTypes.CWT.getCode(),
			FopTypes.CREDIT_CARD.getCode());

	@Override
	public void validate(ExchangeOrder input) {
		if (creditCardTypes.contains(input.getFopType()) && input.getServiceInfo().getFormOfPayment().getCreditCard() == null) {
			throw new IllegalArgumentException("Credit Card required for fopType " + input.getFopType());
		}
	}
}
