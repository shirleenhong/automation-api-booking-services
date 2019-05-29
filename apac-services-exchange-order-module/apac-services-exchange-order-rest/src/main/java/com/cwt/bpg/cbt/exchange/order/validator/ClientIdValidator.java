package com.cwt.bpg.cbt.exchange.order.validator;

import com.cwt.bpg.cbt.exchange.order.model.FeesInput;

public class ClientIdValidator implements Validator<FeesInput> {

	@Override
	public void validate(FeesInput input) {
		if (input.getClientId() == 0) {
			throw new IllegalArgumentException("Client ID required");
		}
	}

}
