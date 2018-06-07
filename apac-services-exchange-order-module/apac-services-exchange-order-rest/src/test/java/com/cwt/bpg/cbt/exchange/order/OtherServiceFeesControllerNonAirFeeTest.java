package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OtherServiceFeesControllerNonAirFeeTest {

    private MockMvc mockMvc;

    @Mock
    private OtherServiceFeesService service;

    @InjectMocks
    private OtherServiceFeesController controller;


    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void shouldReturnFeesBreakdown() throws Exception {
        JSONObject jsonObj = new JSONObject();

    	IndiaProduct product = new IndiaProduct();
    	product.setGst(2D);
    	product.setOt1(5D);
    	product.setOt2(10D);
        
        jsonObj.put("commissionByPercent", true);
        jsonObj.put("costAmount", 100);
        jsonObj.put("commissionPercent", 10);
        jsonObj.put("discountPercent", 2);
        jsonObj.put("acctType", "001");
        jsonObj.put("fopType", 2);
        jsonObj.put("fopMode", 3);
        jsonObj.put("product", product);
        jsonObj.put("profileName", "proName");
        jsonObj.put("countryCode", "IN");
        jsonObj.put("clientType", "Z");

        mockMvc.perform(post("/other-service-fees/non-air-fees/in")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).calculateIndiaNonAirFee(any(InNonAirFeesInput.class));
    }

    @Test
    public void shouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

    	IndiaProduct product = new IndiaProduct();
    	product.setGst(2D);
    	product.setOt1(5D);
    	product.setOt2(10D);
        
        jsonObj.put("commissionByPercent", true);
        jsonObj.put("costAmount", 100);
        jsonObj.put("commissionPercent", 10);
        jsonObj.put("discountPercent", 2);
        jsonObj.put("acctType", "001");
        jsonObj.put("fopType", 2);
        jsonObj.put("fopMode", 3);
        jsonObj.put("product", product);

        mockMvc.perform(post("/other-service-fees/non-air-fees/in")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void shouldReturnBadRequestOnEmptyProduct() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("commissionByPercent", true);
        jsonObj.put("costAmount", 100);
        jsonObj.put("commissionPercent", 10);
        jsonObj.put("discountPercent", 2);
        jsonObj.put("acctType", "001");
        jsonObj.put("fopType", 2);
        jsonObj.put("fopMode", 3);
        jsonObj.put("product", new IndiaProduct());

        mockMvc.perform(post("/other-service-fees/non-air-fees/in")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void shouldReturnBadRequestOnEmptyCostAmount() throws Exception {
        JSONObject jsonObj = new JSONObject();

        IndiaProduct product = new IndiaProduct();
        product.setGst(2D);
        product.setOt1(5D);
        product.setOt2(10D);

        jsonObj.put("commissionByPercent", true);
        jsonObj.put("commissionPercent", 10);
        jsonObj.put("discountPercent", 2);
        jsonObj.put("acctType", "001");
        jsonObj.put("fopType", 2);
        jsonObj.put("fopMode", 3);
        jsonObj.put("product", product);

        mockMvc.perform(post("/other-service-fees/non-air-fees/in")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void shouldReturnBadRequestOnEmptyFOPType() throws Exception {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setClientType("CT");
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
    }

    @Test
    public void shouldReturnBadRequestOnEmptyClientType() throws Exception {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setFopType("CX");
        input.setGstPercent(2D);
        input.setProfileName("PN");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

        input.setClientType("");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestOnEmptyGst() throws Exception {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setFopType("CX");
        input.setProfileName("PN");
        input.setCountryCode("HK");

        mockMvc.perform(post("/other-service-fees/non-air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

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
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
