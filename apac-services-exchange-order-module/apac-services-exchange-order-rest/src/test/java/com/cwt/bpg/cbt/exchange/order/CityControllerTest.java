package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.City;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CityControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CityService service;

    @InjectMocks
    private CityController controller;

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
        mockMvc.perform(get("/cities")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getAll();
    }

    @Test
    public void getCitiesByCodeShouldReturnCities() throws Exception {
        mockMvc.perform(get("/cities/XXX")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getCities("XXX");
    }

    @Test
    public void putCityShouldSaveAndReturnSavedCity() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("code", "XXX");
        jsonObj.put("name", "Test City Name");
        jsonObj.put("regionCode", "XXX");
        jsonObj.put("countryCode", "XXX");
        jsonObj.put("airportCode", "XXX");

        mockMvc.perform(put("/cities")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any(City.class));
    }

    @Test
    public void putCityShouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("name", "Test City Name");
        jsonObj.put("regionCode", "XXX");
        jsonObj.put("countryCode", "XXX");
        jsonObj.put("airportCode", "XXX");

        mockMvc.perform(put("/cities")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void removeCityShouldDeleteCity() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("code", "XXX");
        jsonObj.put("name", "Test City Name");
        jsonObj.put("regionCode", "XXX");
        jsonObj.put("countryCode", "XXX");
        jsonObj.put("airportCode", "XXX");

        mockMvc.perform(delete("/cities")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).delete(any(City.class));
    }

    @Test
    public void removeCityShouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("name", "Test City Name");
        jsonObj.put("regionCode", "XXX");
        jsonObj.put("countryCode", "XXX");
        jsonObj.put("airportCode", "XXX");

        mockMvc.perform(delete("/cities")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

}
