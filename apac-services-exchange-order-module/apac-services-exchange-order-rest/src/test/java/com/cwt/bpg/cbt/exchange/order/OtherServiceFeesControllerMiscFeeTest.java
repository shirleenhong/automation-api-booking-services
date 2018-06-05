package com.cwt.bpg.cbt.exchange.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import com.cwt.bpg.cbt.exchange.order.model.HkSgNonAirFeesInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OtherServiceFeesControllerMiscFeeTest {

	private MockMvc mockMvc;
	
	@Mock
	private OtherServiceFeesService service;
	
	@InjectMocks
	private OtherServiceFeesController controller = new OtherServiceFeesController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.build();
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),                        
            Charset.forName("utf8")                     
            );
	
	@Test
	public void shouldReturnFeesBreakdown() throws Exception {

		HkSgNonAirFeesInput input = new HkSgNonAirFeesInput();
		
		input.setFopType("CX");	  
	    input.setClientType("CT");
	    input.setGstPercent(2D);
	    input.setProfileName("PN");
	    
        mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isOk());
	}

	
	@Test
	public void shouldReturnBadRequestOnEmptyFOPType() throws Exception {
						
		HkSgNonAirFeesInput input = new HkSgNonAirFeesInput();
		
	    input.setClientType("CT");
	    input.setGstPercent(2D);
	    input.setProfileName("PN");
	    
	    mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
		
		input.setFopType("");
		
		mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyClientType() throws Exception {
						
		HkSgNonAirFeesInput input = new HkSgNonAirFeesInput();
		
	    input.setFopType("CX");
	    input.setGstPercent(2D);
	    input.setProfileName("PN");
	    
	    mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
		
	    input.setClientType("");
		
		mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyGst() throws Exception {
						
		HkSgNonAirFeesInput input = new HkSgNonAirFeesInput();
		
	    input.setFopType("CX");
	    input.setProfileName("PN");
        input.setCountryCode("HK");
	    
	    mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyProdName() throws Exception {
						
		HkSgNonAirFeesInput input = new HkSgNonAirFeesInput();
		
	    input.setFopType("CX");
	    input.setGstPercent(2D);
	    
	    mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
		
	    input.setProfileName("");
		
		mockMvc.perform(post("/other-service-fees/non-air-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
