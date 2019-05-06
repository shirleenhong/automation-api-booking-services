package com.cwt.bpg.cbt.smartflow.validator;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.smartflow.model.Codif;

@Component
public class CodifValidator {

	public void validate(Codif codifInput) {
		if (codifInput.getHarpNo() != null && codifInput.getHarpNo().length() > 6) {

			throw new IllegalArgumentException("HarpNo should be not less than 6 characters.");
		}
	}

}
