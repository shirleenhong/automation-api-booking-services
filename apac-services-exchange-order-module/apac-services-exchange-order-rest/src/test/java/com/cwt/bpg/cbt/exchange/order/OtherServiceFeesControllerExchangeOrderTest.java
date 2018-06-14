package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OtherServiceFeesControllerExchangeOrderTest {

	private MockMvc mockMvc;
	
	@Mock
	private OtherServiceFeesService service;
	
	@InjectMocks
	private OtherServiceFeesController controller;
	
	private String url;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.build();
		
		controller = new OtherServiceFeesController();
		
		url = "/other-service-fees/exchange-order";
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),                        
            Charset.forName("utf8")                     
            );
	
	@Test
	public void shouldReturnExchangeOrderNumber() throws Exception {
						
		ExchangeOrder order = new ExchangeOrder();

		when(service.saveExchangeOrder(order)).thenReturn(anyString());

		mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(order)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();

		verify(service,times(1)).saveExchangeOrder(any(ExchangeOrder.class));
	}
	
			
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
