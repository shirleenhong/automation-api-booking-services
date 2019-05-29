package com.cwt.bpg.cbt.client.gst.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoResponse;
import com.cwt.bpg.cbt.client.gst.service.ClientGstInfoService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientGstInfoControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ClientGstInfoService service;

	@InjectMocks
	private ClientGstInfoController controller;

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getAllClientGstInfoReturns200() throws Exception {
		List<ClientGstInfo> clientGstInfos = new ArrayList<>();

	    when(service.getAllClientGstInfo()).thenReturn(Arrays.asList(new ClientGstInfo()));
			
	    mockMvc.perform(get("/client-gst-info").contentType(APPLICATION_JSON_UTF8)
					.content(convertObjectToJsonBytes(clientGstInfos))).andExpect(status().isOk()).andReturn()
					.getResponse();

		verify(service).getAllClientGstInfo();
	}
	
	@Test
	public void getClientGstInfoReturns200() throws Exception {
		Set<String> airlineCodes = new HashSet<>();
		airlineCodes.add("AF");
		airlineCodes.add("XS");
		
		ClientGstInfoResponse response = new ClientGstInfoResponse();
		response.setAirlineCodes(airlineCodes);
		
		when(service.getClientGstInfo(any(), any())).thenReturn(response);
		
		final String gstin = "1234";
		
		mockMvc.perform(get("/client-gst-info/" + gstin)
				.param("airlineCodes", "AF").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andReturn()
				.getResponse();
		
		verify(service).getClientGstInfo(any(), any());
	}
	
	@Test
	public void getClientGstInfoReturns404() throws Exception {
		when(service.getClientGstInfo(any(), any())).thenReturn(null);
		
		final String gstin = "1234";
		
		mockMvc.perform(get("/client-gst-info/" + gstin)
				.param("airlineCodes", "AF").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isNotFound()).andReturn()
				.getResponse();
		
		verify(service).getClientGstInfo(any(), any());
	}
	
	@Test
	public void getClientGstInfoReturns204() throws Exception {
		ClientGstInfoResponse response = new ClientGstInfoResponse();
		response.setAirlineCodes(new HashSet<>());
		
		when(service.getClientGstInfo(any(), any())).thenReturn(response);
		
		final String gstin = "1234";
		
		mockMvc.perform(get("/client-gst-info/" + gstin)
				.param("airlineCodes", "AF").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isNoContent()).andReturn()
				.getResponse();
		
		verify(service).getClientGstInfo(any(), any());
	}


	@Test
	public void putClientGstInfoReturns200() throws Exception {
		ClientGstInfo clientGstInfo = new ClientGstInfo();
		clientGstInfo.setGstin("gstin");
		clientGstInfo.setClient("client");
		clientGstInfo.setClientEntityName("clientEntityName");
		clientGstInfo.setBusinessPhoneNumber("businessPhoneNumber");
		clientGstInfo.setBusinessEmailAddress("businessEmailAddress");
		clientGstInfo.setEntityAddressLine1("entityAddressLine1");
		clientGstInfo.setBusinessPhoneNumber("businessPhoneNumber");
		clientGstInfo.setPostalCode("postalCode");
		clientGstInfo.setCity("city");
		clientGstInfo.setState("state");

		when(service.save(any(ClientGstInfo.class))).thenReturn(clientGstInfo);

		mockMvc.perform(put("/client-gst-info").contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(clientGstInfo))).andExpect(status().isOk()).andReturn().getResponse();

		verify(service).save(any(ClientGstInfo.class));
	}

	@Test
	public void removeClientGstInfoReturns200UponSuccessfulDeletion() throws Exception {
		when(service.remove("ABC123")).thenReturn("ABC123");

		mockMvc.perform(delete("/client-gst-info/ABC123").contentType(APPLICATION_JSON_UTF8).content("ABC123"))
				.andExpect(status().isOk()).andReturn().getResponse();

		verify(service).remove("ABC123");
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}