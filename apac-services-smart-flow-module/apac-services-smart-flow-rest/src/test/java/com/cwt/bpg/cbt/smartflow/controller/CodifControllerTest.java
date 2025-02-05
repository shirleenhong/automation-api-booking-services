package com.cwt.bpg.cbt.smartflow.controller;

import static org.mockito.Mockito.*;
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

import com.cwt.bpg.cbt.smartflow.model.Codif;
import com.cwt.bpg.cbt.smartflow.service.CodifService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CodifControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;
    @Mock
    private CodifService service;
    @InjectMocks
    private CodifController controller;

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getCodifsReturns200() throws Exception {
        List<Codif> codifs = new ArrayList<>();
        when(service.getCodifsByGdsPropIdAndKeyType(anyString(), anyString())).thenReturn(codifs);

        mockMvc.perform(get("/codif/harpno/123456/AC")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(codifs)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).getCodifsByGdsPropIdAndKeyType("123456", "AC");
    }

    @Test
    public void getAgentReturns200WhenCodifExists() throws Exception {
        Codif codif = new Codif();
        when(service.getCodif("ABC123", "1A")).thenReturn(codif);

        mockMvc.perform(get("/codif/ABC123/1A")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(codif)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).getCodif("ABC123", "1A");
    }

    @Test
    public void getAgentReturns204WhenCodifDoesNotExist() throws Exception {
        when(service.getCodif("CBA321", "1A")).thenReturn(null);

        mockMvc.perform(get("/codif/CBA321/1A")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();

        verify(service).getCodif("CBA321", "1A");
    }

    @Test
    public void putCodifReturns200() throws Exception {
        Codif agent = new Codif();
        agent.setHarpNo("123456");
        agent.setGdsPropId("123456789");
        agent.setKeyType("1A");

        when(service.save(any(Codif.class))).thenReturn(agent);

        mockMvc.perform(put("/codif")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(agent)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).save(any(Codif.class));
    }

    @Test
    public void removeCodifReturns200UponSuccessfulDeletion() throws Exception {
        when(service.remove("5b2870d6284b8d1ac84300ad")).thenReturn("5b2870d6284b8d1ac84300ad");

        mockMvc.perform(delete("/codif/5b2870d6284b8d1ac84300ad")
                .contentType(APPLICATION_JSON_UTF8).content("ABC123"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).remove("5b2870d6284b8d1ac84300ad");
    }

    @Test
    public void removeCodifReturns404WhenThereIsNoRecordToDelete() throws Exception {
        when(service.remove("5b2870d6284b8d1ac84300ad")).thenReturn("");

        mockMvc.perform(delete("/codif/5b2870d6284b8d1ac84300ad")
                .contentType(APPLICATION_JSON_UTF8).content(""))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        verify(service).remove("5b2870d6284b8d1ac84300ad");
    }
}
