package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ValidationError;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientGstInfoListValidatorServiceTest {

    @Mock
    private Validator validator;

    private List<ValidationError> validationErrors;

    @InjectMocks
    private ClientGstInfoListValidatorService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validationErrors = new LinkedList<>();
    }

    @Test
    public void shouldPopulateValidationErrors() {
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("gstin");

        ConstraintViolation<ClientGstInfo> violation = mock(ConstraintViolation.class);
        when(violation.getInvalidValue()).thenReturn("sample&&&");
        when(violation.getMessage()).thenReturn("gstin should be alphanumeric");
        when(violation.getPropertyPath()).thenReturn(path);

        Set<ConstraintViolation<ClientGstInfo>> mockValidation = new HashSet<>(Arrays.asList(
            violation
        ));

        when(validator.validate(any(ClientGstInfo.class))).thenReturn(mockValidation);

        service.validate(1, new ClientGstInfo(), validationErrors);

        assertEquals(1, validationErrors.size());
        assertEquals(1, validationErrors.get(0).getRowNumber());
        assertEquals("gstin", validationErrors.get(0).getErrors().get(0).getField());
        assertEquals("sample&&&", validationErrors.get(0).getErrors().get(0).getValue());
        assertEquals("gstin should be alphanumeric", validationErrors.get(0).getErrors().get(0).getMessage());
    }

    @Test
    public void shouldNotPopulateValidationErrorsIfValidatorReturnsEmptySet() {
        when(validator.validate(any(ClientGstInfo.class))).thenReturn(Collections.emptySet());

        assertTrue(validationErrors.isEmpty());
    }
}