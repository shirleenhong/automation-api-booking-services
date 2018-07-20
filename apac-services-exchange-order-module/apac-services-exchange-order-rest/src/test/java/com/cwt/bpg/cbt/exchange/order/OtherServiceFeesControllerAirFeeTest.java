package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.exchange.order.OtherServiceFeesControllerNonAirFeeTest.convertObjectToJsonBytes;
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

import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.validator.AirlineOverheadCommissionValidator;
import com.cwt.bpg.cbt.exchange.order.validator.FeeValidator;
import com.cwt.bpg.cbt.exchange.order.validator.OthTaxValidator;

import net.minidev.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OtherServiceFeesControllerAirFeeTest {

    private MockMvc mockMvc;

    @Mock
    private OtherServiceFeesService service;
    
    @Mock
	private AirlineOverheadCommissionValidator airlineOverheadCommissionValidator;

    @Mock
	private OthTaxValidator othTaxValidator;

    @Mock
	private FeeValidator feeValidator;

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

        jsonObj.put("clientType", "Z");
        jsonObj.put("clientAccountNumber", "testName");
        jsonObj.put("fopType", "Z");
        jsonObj.put("applyFormula", 111);
        jsonObj.put("commissionByPercent", 111);

        mockMvc.perform(post("/other-service-fees/air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).calculateAirFees(any(AirFeesInput.class), anyString());
    }

    @Test
    public void shouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("clientType", "");
        jsonObj.put("clientAccountNumber", "testNumber");
        jsonObj.put("fopType", "Z");
        jsonObj.put("applyFormula", 111);
        jsonObj.put("commissionByPercent", 111);
        jsonObj.put("countryCode","HK");
        jsonObj.put("gstPercent","1");

        mockMvc.perform(post("/other-service-fees/air-fees/hk")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }
    
    @Test
    public void shouldReturnIndiaAirFees() throws Exception {
    	
    	JSONObject jsonObj = new JSONObject();

        jsonObj.put("countryCode", "IN");
        jsonObj.put("clientType", "Z");
        jsonObj.put("clientAccountNumber", "testName");
        jsonObj.put("fopType", "Z");
        jsonObj.put("applyFormula", 111);
        jsonObj.put("commissionByPercent", 111);
        jsonObj.put("baseFare", 111);
        jsonObj.put("tripType", "TR");
        jsonObj.put("yqTax", 100);
       
        mockMvc.perform(post("/other-service-fees/air-fees/in")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).calculateIndiaAirFees(any(IndiaAirFeesInput.class));
    }

}
