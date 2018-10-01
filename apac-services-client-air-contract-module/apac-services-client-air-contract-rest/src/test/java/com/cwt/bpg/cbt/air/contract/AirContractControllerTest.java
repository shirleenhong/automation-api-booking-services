package com.cwt.bpg.cbt.air.contract;

import com.cwt.bpg.cbt.air.contract.model.AirContract;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
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

import java.io.IOException;
import java.nio.charset.Charset;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AirContractControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirContractService service;

    @InjectMocks
    private AirContractController controller;

	private AirContract airContract;

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

		airContract = new AirContract();
		airContract.setCountryCode("SG");
		airContract.setAirlineCode("BR");
		airContract.setClientAccountNumber("3407002");
    }

    @Test
    public void getAirContractShouldReturnAirContract() throws Exception {

        when(service.getAirContract(airContract.getCountryCode(), airContract.getAirlineCode(), airContract.getClientAccountNumber()))
			.thenReturn(airContract);

        mockMvc.perform(get("/air-contract/"+
			airContract.getCountryCode() +"/"+
			airContract.getAirlineCode() +"/"+
			airContract.getClientAccountNumber())
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(airContract)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).getAirContract(
			airContract.getCountryCode(),
			airContract.getAirlineCode(),
			airContract.getClientAccountNumber());
    }

    @Test
    public void putAirContractShouldSaveAndReturnSavedAirContract() throws Exception {

		when(service.save(any(AirContract.class))).thenReturn(airContract);

        mockMvc.perform(put("/air-contract")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(airContract)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any(AirContract.class));
    }

    @Test
    public void removeAirContractShouldRemoveAirContract() throws Exception {
		String id = new ObjectId().toString();

		when(service.delete(anyString())).thenReturn(id);

        mockMvc.perform(delete("/air-contract/"+ id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).delete(anyString());
    }

	@Test
	public void removeAirContractShouldReturnNoFoundWhenRecordDoesNotExist() throws Exception {
		String id = new ObjectId().toString();

		when(service.delete(id)).thenReturn("");

		mockMvc.perform(delete("/air-contract/" + id)
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andReturn()
				.getResponse();

		verify(service, times(1)).delete(id);
	}

	static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
