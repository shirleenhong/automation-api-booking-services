package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.FieldError;
import com.cwt.bpg.cbt.client.gst.model.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientGstInfoListValidatorService {

    @Autowired
    private Validator validator;

    public void validate(int rowNumber, ClientGstInfo clientGstInfo, List<ValidationError> validationErrors) {
        Set<ConstraintViolation<ClientGstInfo>> result = validator.validate(clientGstInfo);
        if (result.isEmpty()) {
            return;
        }
        List<FieldError> fieldErrors = result.stream().map(violation -> FieldError
                .create(violation.getPropertyPath().toString(),
                        violation.getInvalidValue(),
                        violation.getMessage())).collect(Collectors.toList());
        validationErrors.add(ValidationError.create(rowNumber, fieldErrors));
    }
}
