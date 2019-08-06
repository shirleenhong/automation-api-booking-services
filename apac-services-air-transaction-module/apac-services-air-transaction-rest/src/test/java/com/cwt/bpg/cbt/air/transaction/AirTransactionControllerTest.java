package com.cwt.bpg.cbt.air.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AirTransactionControllerTest
{

    private MockMvc mockMvc;

    @Mock
    private AirTransactionService service;

    @InjectMocks
    private AirTransactionController controller;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAirTransactionShouldReturnAirTransaction() throws Exception
    {
        AirTransactionOutput output = new AirTransactionOutput();
        output.setPassthroughType(PassthroughType.CWT);

        when(service.getAirTransaction(any(AirTransactionInput.class))).thenReturn(output);

        mockMvc.perform(get("/air-transactions?countryCode=IN&airlineCode=&bookingClasses=&ccVendorCode=&ccType=")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(output)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).getAirTransaction(any(AirTransactionInput.class));
    }

    @Test
    public void getAirTransactionsShouldReturnAirTransactions() throws Exception
    {

        when(service.getAirTransactionList(any(AirTransactionInput.class))).thenReturn(anyList());

        mockMvc.perform(get("/air-transactions/IN/SQ?clientAccountNumber=")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getAirTransactionList(any(AirTransactionInput.class));
    }

    @Test
    public void putAirTransactionShouldSaveAndReturnSavedAirTransaction() throws Exception
    {
        when(service.save(any())).thenReturn(Arrays.asList(new AirTransaction()));

        mockMvc.perform(put("/air-transactions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(Arrays.asList(new AirTransaction()))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any());
    }

    @Test
    public void removeAirTransactionShouldRemoveAirTransaction() throws Exception
    {

        String id = "ID";
        when(service.delete(id)).thenReturn(id);

        mockMvc.perform(delete("/air-transactions/" + id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).delete(anyString());
    }

    @Test
    public void removeAirTransactionShouldReturnNotFoundWhenRecordDoesNotExist() throws Exception
    {
        String id = "ID";
        when(service.delete(id)).thenReturn("");

        mockMvc.perform(delete("/air-transactions/" + id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        verify(service, times(1)).delete(anyString());
    }

    @Test
    public void uploadAirTransctionShould200() throws Exception
    {
        String filename = "airTransactions.xlsx";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", filename, "text/plain", "This is a Test".getBytes());
    
        mockMvc.perform(multipart("/air-transactions")
                .file(mockMultipartFile)
                .param("file", filename));
        
        verify(service, times(1)).upload(any(), anyString());
    }

    static byte[] convertObjectToJsonBytes(Object object) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
