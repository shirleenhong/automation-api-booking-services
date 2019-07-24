package com.cwt.bpg.cbt.client.gst.model.constraint;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GdsEmailValidator implements ConstraintValidator<GdsEmailFormat, String> {
    private static final int VALID_EMAIL_LENGTH = 35;

    @Override
    public void initialize(GdsEmailFormat annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value)) {
            return true;
        }
        final String replaceEmailChars = value.replace("@", "//").replace("_", "..").replace("-", "./");
        return VALID_EMAIL_LENGTH >= replaceEmailChars.length();
    }
}
