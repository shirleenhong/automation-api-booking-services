package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OtherServiceFeesControllerNonAirFeeTest {

    private MockMvc mockMvc;

    @Mock
    private OtherServiceFeesService service;

    @InjectMocks
    private OtherServiceFeesController controller = new OtherServiceFeesController();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    @Test
    public void shouldReturnFeesBreakdown() throws Exception {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setFopType("CX");
        input.setGstPercent(2D);
        input.setProfileName("PN");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isOk());

        verify(service, times(1)).calculateNonAirFees(any(NonAirFeesInput.class));
    }


    @Test
    public void shouldReturnBadRequestOnEmptyFOPType() throws Exception {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setGstPercent(2D);
        input.setProfileName("PN");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

        input.setFopType("");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void shouldReturnOkOnEmptyGst() throws Exception {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setFopType("CX");
        input.setProfileName("PN");
        input.setCountryCode("HK");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isOk());

        verify(service, times(1)).calculateNonAirFees(any(NonAirFeesInput.class));
    }

    @Test
    public void shouldReturnBadRequestOnEmptyProdName() throws Exception {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setFopType("CX");
        input.setGstPercent(2D);

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

        input.setProfileName("");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
