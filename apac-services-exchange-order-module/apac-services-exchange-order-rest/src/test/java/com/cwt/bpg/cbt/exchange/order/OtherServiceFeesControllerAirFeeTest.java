package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.exchange.order.OtherServiceFeesControllerNonAirFeeTest.convertObjectToJsonBytes;
import static org.mockito.Matchers.any;
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

import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;

import net.minidev.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OtherServiceFeesControllerAirFeeTest {

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

        jsonObj.put("countryCode", "SG");
        jsonObj.put("clientType", "Z");
        jsonObj.put("profileName", "testName");
        jsonObj.put("fopType", "Z");
        jsonObj.put("applyFormula", 111);
        jsonObj.put("commissionByPercent", 111);

        mockMvc.perform(post("/other-service-fees/air-fees/sg")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).calculateAirFees(any(AirFeesInput.class));
    }

    @Test
    public void shouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("clientType", "");
        jsonObj.put("profileName", "testName");
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
}
