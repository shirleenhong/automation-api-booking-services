package com.cwt.bpg.cbt.client.gst.model.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GdsEmailValidator implements ConstraintValidator<GdsEmailFormat, String> {
    private static final int VALID_EMAIL_LENGTH = 35;

    @Override
    public void initialize(GdsEmailFormat annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final String replaceEmailChars = value.replace("@", "//").replace("_", "..").replace("-", "./");
        return VALID_EMAIL_LENGTH >= replaceEmailChars.length();
    }
}
