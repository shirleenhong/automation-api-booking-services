package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NettCostInput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OtherServiceFeesControllerNettCostTest {

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
						
		NettCostInput input = new NettCostInput();
	    input.setCommissionPct(2D);
	    input.setSellingPrice(new BigDecimal(250));

		AirFeesBreakdown breakdown = new AirFeesBreakdown();
		when(service.calculateNettCost(input)).thenReturn(breakdown);

		mockMvc.perform(post("/other-service-fees/nett-cost")
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(input)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();

		verify(service,times(1)).calculateNettCost(any(NettCostInput.class));
	}
	
	@Test
	public void shouldReturnBadRequestOnEmptyInput() throws Exception {
						
		NettCostInput input = new NettCostInput();
	    
	    mockMvc.perform(post("/other-service-fees/nett-cost")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());
	    
	    input.setCommissionPct(0D);
	    
	    mockMvc.perform(post("/other-service-fees/nett-cost")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(input)))
                .andExpect(status().isBadRequest());

	    verifyZeroInteractions(service);
	}
			
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
