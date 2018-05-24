package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.exchange.order.OtherServiceFeesControllerMiscFeeTest.convertObjectToJsonBytes;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
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

import com.cwt.bpg.cbt.exchange.order.model.InMiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.InProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;

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

    	InProduct product = new InProduct();
    	product.setGst(2);
    	product.setOt1("5");
    	product.setOt1("10");
        
        jsonObj.put("commissionByPercent", true);
        jsonObj.put("costAmount", 100);
        jsonObj.put("commissionPercent", 10);
        jsonObj.put("discountPercent", 2);
        jsonObj.put("acctType", "001");
        jsonObj.put("fopType", 2);
        jsonObj.put("fopMode", 3);
        jsonObj.put("product", product);
        jsonObj.put("profileName", "proName");

        mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).calculateNonAirFee(any(InMiscFeesInput.class));
    }

    @Test
    public void shouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

    	InProduct product = new InProduct();
    	product.setGst(2);
    	product.setOt1("5");
    	product.setOt1("10");
        
        jsonObj.put("commissionByPercent", true);
        jsonObj.put("costAmount", 100);
        jsonObj.put("commissionPercent", 10);
        jsonObj.put("discountPercent", 2);
        jsonObj.put("acctType", "001");
        jsonObj.put("fopType", 2);
        jsonObj.put("fopMode", 3);
        jsonObj.put("product", product);

        mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }
}
