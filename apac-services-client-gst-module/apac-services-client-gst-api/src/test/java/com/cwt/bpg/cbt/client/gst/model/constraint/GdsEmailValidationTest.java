package com.cwt.bpg.cbt.client.gst.model.constraint;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.*;

import org.junit.Test;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;

public class GdsEmailValidationTest {

    @Test
    public void canValidateGdsEmailFormat() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ClientGstInfo clientGstInfo = new ClientGstInfo();
        clientGstInfo.setGstin("1234567890ABCDE");
        clientGstInfo.setClient("client");
        clientGstInfo.setClientEntityName("clientEntityName");
        clientGstInfo.setBusinessPhoneNumber("2211996");
        clientGstInfo.setEntityAddressLine1("entityAddressLine1");
        clientGstInfo.setPostalCode("9200");
        clientGstInfo.setCity("city");
        clientGstInfo.setState("state");
        clientGstInfo.setBusinessEmailAddress("sample-mini_test1234567@sgmail.com");

        Set<ConstraintViolation<ClientGstInfo>> constraintViolations = validator.validate(clientGstInfo);

        assertEquals(1, constraintViolations.size());
        assertEquals("GDS Email is not valid or more than 35 chars after transformation",
                constraintViolations.iterator().next().getMessage());
    }

}
