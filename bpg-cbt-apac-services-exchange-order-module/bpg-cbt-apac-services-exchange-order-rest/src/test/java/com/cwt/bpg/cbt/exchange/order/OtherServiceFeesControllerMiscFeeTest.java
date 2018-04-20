package com.cwt.bpg.cbt.exchange.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OtherServiceFeesControllerMiscFeeTest {

	private MockMvc mockMvc;
	
	@Mock
	private OtherServiceFeesApi api;
	
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
						
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
		input.setMarketCode("HK");
	    input.setCurrencyCode("HKD");
	    input.setFopType("CX");	  
	    input.setClientType("CT");
	    input.setGstPercent(2D);
	    input.setProductName("PN");
	    
        mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isOk());
	}

	@Test
	public void shouldReturnBadRequestOnEmptyMarketCode() throws Exception {
						
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
	    input.setCurrencyCode("HKD");
	    input.setFopType("CX");
	    input.setClientType("CT");
	    input.setGstPercent(2D);
	    input.setProductName("PN");
				
		mockMvc.perform(post("/other-service-fees/misc-fees")
	                .contentType(APPLICATION_JSON_UTF8)
	                .content(convertObjectToJsonBytes(input)))
	                .andExpect(status().isBadRequest());
		
		input.setMarketCode("");	
		
		mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyCurrencyCode() throws Exception {
						
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
	    input.setMarketCode("HK");
	    input.setFopType("CX");
	    input.setClientType("CT");
	    input.setGstPercent(2D);
	    input.setProductName("PN");
	    
	    mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
		
		input.setCurrencyCode("");
		mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyFOPType() throws Exception {
						
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
	    input.setMarketCode("HK");
	    input.setCurrencyCode("HKD");
	    input.setClientType("CT");
	    input.setGstPercent(2D);
	    input.setProductName("PN");
	    
	    mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
		
		input.setFopType("");
		
		mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyClientType() throws Exception {
						
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
	    input.setMarketCode("HK");
	    input.setCurrencyCode("HKD");
	    input.setFopType("CX");
	    input.setGstPercent(2D);
	    input.setProductName("PN");
	    
	    mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
		
	    input.setClientType("");
		
		mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyGst() throws Exception {
						
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
	    input.setMarketCode("HK");
	    input.setCurrencyCode("HKD");
	    input.setFopType("CX");
	    input.setProductName("PN");
	    
	    mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyProdName() throws Exception {
						
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
	    input.setMarketCode("HK");
	    input.setCurrencyCode("HKD");
	    input.setFopType("CX");
	    input.setGstPercent(2D);
	    
	    mockMvc.perform(post("/other-service-fees/misc-fees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
		
	    input.setProductName("");
		
		mockMvc.perform(post("/other-service-fees/misc-fees")
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
