package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.exchange.order.OtherServiceFeesControllerNonAirFeeTest.convertObjectToJsonBytes;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirProductInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import net.minidev.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OtherServiceFeesControllerIndiaNonAirFeeTest {

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

        IndiaNonAirProductInput product = new IndiaNonAirProductInput();
    	product.setGstPercent(2D);
    	product.setOt1Percent(5D);
    	product.setOt2Percent(10D);
    	product.setProductCode("1");
        
        jsonObj.put("commissionByPercent", true);
        jsonObj.put("costAmount", 100);
        jsonObj.put("commissionPercent", 10);
        jsonObj.put("discountPercent", 2);
        jsonObj.put("acctType", "001");
        jsonObj.put("fopType", 2);
        jsonObj.put("fopMode", 3);
        jsonObj.put("fopNumber", "1");
        jsonObj.put("product", product);
        jsonObj.put("clientAccountNumber", "caNumber");
        jsonObj.put("countryCode", Country.INDIA.getCode());
        jsonObj.put("clientType", "Z");
        jsonObj.put("clientId", 123);
        
        mockMvc.perform(post("/other-service-fees/non-air-fees/in")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).calculateIndiaNonAirFees(any(IndiaNonAirFeesInput.class));
    }

    @Test
    public void shouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

    	IndiaProduct product = new IndiaProduct();
    	product.setGstPercent(2D);
    	product.setOt1Percent(5D);
    	product.setOt2Percent(10D);
        
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
        product.setGstPercent(2D);
        product.setOt1Percent(5D);
        product.setOt2Percent(10D);

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
}
