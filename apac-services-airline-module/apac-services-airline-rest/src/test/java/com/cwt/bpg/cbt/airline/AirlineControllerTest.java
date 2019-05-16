package com.cwt.bpg.cbt.airline;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.airline.model.Airline;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AirlineControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirlineService service;

    @InjectMocks
    private AirlineController controller;

    private static final MediaType APPLICATION_JSON_UTF8 =  new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAirlinesReturns200() throws Exception {
        List<Airline> airlines = new ArrayList<>();
        when(service.getAirlines()).thenReturn(airlines);

        mockMvc.perform(get("/airlines")
            .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(airlines)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).getAirlines();
    }

    @Test
    public void getAirlineReturns200WhenAirlineExists() throws Exception {
        Airline airline = new Airline();
        when(service.getAirline("SG")).thenReturn(airline);

        mockMvc.perform(get("/airlines/SG")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(airline)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).getAirline("SG");
    }

    @Test
    public void getAirlineReturns204WhenAirlineDoesNotExist() throws Exception {
        when(service.getAirline("YY")).thenReturn(null);

        mockMvc.perform(get("/airlines/YY")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();

        verify(service).getAirline("YY");
    }

    @Test
    public void putAirlineReturns200() throws Exception {
        Airline airline = new Airline();
        airline.setIataCode("YY");
        airline.setName("YY Air");
        when(service.save(any(Airline.class))).thenReturn(airline);

        mockMvc.perform(put("/airlines")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(airline)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).save(any(Airline.class));
    }

    @Test
    public void removeAirlineReturns200UponSuccessfulDeletion() throws Exception {
        when(service.delete("YY")).thenReturn("YY");

        mockMvc.perform(delete("/airlines/YY")
                .contentType(APPLICATION_JSON_UTF8).content("YY"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).delete("YY");
    }

    @Test
    public void removeAirlineReturns404WhenThereIsNoRecordToDelete() throws Exception {
        when(service.delete("YY")).thenReturn("");

        mockMvc.perform(delete("/airlines/YY")
                .contentType(APPLICATION_JSON_UTF8).content(""))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        verify(service).delete("YY");
    }

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
