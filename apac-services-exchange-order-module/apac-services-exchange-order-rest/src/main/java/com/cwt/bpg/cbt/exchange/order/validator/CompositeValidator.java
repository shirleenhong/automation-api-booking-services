package com.cwt.bpg.cbt.exchange.order.validator;

import java.util.ArrayList;
import java.util.List;

public class CompositeValidator<V extends Validator<A>, A> implements Validator<A> {

	private List<V> validators = new ArrayList<>();

    public List<V> getValidators() {
        return validators;
    }

    public void setValidators(List<V> validators) {
        this.validators = validators;
    }

    public void addValidator(V validator) {
        validators.add(validator);
    }

    @Override
	public void validate(A obj) {
		validators.forEach(validator -> validator.validate(obj));
	}
}
