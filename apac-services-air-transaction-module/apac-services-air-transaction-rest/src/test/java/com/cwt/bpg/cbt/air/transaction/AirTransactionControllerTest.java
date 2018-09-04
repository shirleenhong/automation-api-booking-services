package com.cwt.bpg.cbt.air.transaction;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AirTransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirTransactionService service;

    @InjectMocks
    private AirTransactionController controller;

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
    public void getAirTransactionShouldReturnAirTransaction() throws Exception {
		AirTransactionOutput output = new AirTransactionOutput();
		output.setPassthroughType(PassthroughType.CWT);

        when(service.getAirTransaction(any(AirTransactionInput.class))).thenReturn(output);

        mockMvc.perform(get("/air-transaction?airlineCode=&bookingClasses=&ccVendorCode=&ccType=")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(output)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).getAirTransaction(any(AirTransactionInput.class));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void getAirTransactionsShouldReturnAirTransactions() throws Exception {

        when(service.getAirTransactionList(any(AirTransactionInput.class))).thenReturn(anyList());

        mockMvc.perform(get("/air-transactions?airlineCode=&clientAccountNumber=")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getAirTransactionList(any(AirTransactionInput.class));
    }

    @Test
    public void putAirTransactionShouldSaveAndReturnSavedAirTransaction() throws Exception {
		AirTransaction airTransaction = new AirTransaction();

		when(service.save(any(AirTransaction.class))).thenReturn(airTransaction);

        mockMvc.perform(put("/air-transactions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(airTransaction)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any(AirTransaction.class));
    }

    @Test
    public void removeAirTransactionShouldRemoveAirTransaction() throws Exception {

        String id = "ID";
        when(service.delete(id)).thenReturn(id);

        mockMvc.perform(delete("/air-transactions?id=" + id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).delete(anyString());
    }

    @Test
    public void removeAirTransactionShouldReturnNotFoundWhenRecordDoesNotExist() throws Exception {

        String id = "ID";
        when(service.delete(id)).thenReturn("");

        mockMvc.perform(delete("/air-transactions?id=" + id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        verify(service, times(1)).delete(anyString());
    }

	static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
