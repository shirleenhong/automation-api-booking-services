package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.Airport;
import net.minidev.json.JSONObject;
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

import java.nio.charset.Charset;

import static com.cwt.bpg.cbt.exchange.order.OtherServiceFeesControllerMiscFeeTest.convertObjectToJsonBytes;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AirportControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AirportService service;

    @InjectMocks
    private AirportController controller;

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
    public void getCitiesShouldReturnCities() throws Exception {
        mockMvc.perform(get("/airports")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getAll();
    }

    @Test
    public void getCitiesByCodeShouldReturnCities() throws Exception {
        mockMvc.perform(get("/airports/XXX")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getAirport("XXX");
    }

    @Test
    public void putAirportShouldSaveAndReturnSavedAirport() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("code", "XXX");
        jsonObj.put("name", "Test Airport Name");
        jsonObj.put("regionCode", "XXX");
        jsonObj.put("countryCode", "XXX");
        jsonObj.put("cityCode", "XXX");

        mockMvc.perform(put("/airports")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any(Airport.class));
    }

    @Test
    public void putAirportShouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("name", "Test Airport Name");
        jsonObj.put("regionCode", "XXX");
        jsonObj.put("countryCode", "XXX");
        jsonObj.put("cityCode", "XXX");

        mockMvc.perform(put("/airports")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void removeAirportShouldDeleteAirport() throws Exception {

        mockMvc.perform(delete("/airports/XXX")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        verify(service, times(1)).delete(anyString());
    }

    @Test
    public void removeAirportShouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        mockMvc.perform(delete("/airports")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verifyZeroInteractions(service);
    }

}