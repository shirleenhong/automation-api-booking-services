package com.cwt.bpg.cbt.exchange.order.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CompositeValidator implements Validator<Object> {

	private List<Validator> validators = new ArrayList<>();

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public void addValidator(Validator validator) {
        validators.add(validator);
    }

    @SuppressWarnings("unchecked")
    @Override
	public void validate(Object obj) {
		validators.forEach(validator -> validator.validate(obj));
	}
}
