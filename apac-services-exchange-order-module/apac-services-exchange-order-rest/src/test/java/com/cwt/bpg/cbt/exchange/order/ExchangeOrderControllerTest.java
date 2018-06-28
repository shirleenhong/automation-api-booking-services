package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ExchangeOrderControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private ExchangeOrderService eoService;
	
	@InjectMocks
	private ExchangeOrderController controller;
	
	private String url;
	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.build();
		
		url = "/exchange-order";
		
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),                        
            Charset.forName("utf8")                     
            );
	
	@Test
	public void shouldReturnExchangeOrderNumber() throws Exception {
						
		ExchangeOrder order = createExchangeOrder();
		
		when(eoService.saveExchangeOrder(order)).thenReturn(anyObject());

		mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(order)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();

		verify(eoService,times(1)).saveExchangeOrder(any(ExchangeOrder.class));
	}
	
	@Test
	public void shouldGetExchangeOrderNumber() {

		String eoNumber = "1806100005";
		
		ResponseEntity<?> result = controller.getExchangeOrder(eoNumber);
		verify(eoService, times(1)).getExchangeOrder(eoNumber);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	
	}
	
	private ExchangeOrder createExchangeOrder() {
		
		ExchangeOrder order = new ExchangeOrder();
		order.setFopType("CX");
		order.setDescription("test_description");
		order.setAdditionalInfoDate(Instant.now());
		order.setProductCode("PR01");
		order.setVendorCode("VEN090909");
		order.setPnr("PNR1234");
		order.setAccountNumber("987654321");
		order.setPassengerName("Passenger");
		order.setAgentId("U001XXX");
		order.setPcc("SIN1234");
		
		CreditCard creditCard = new CreditCard();
		creditCard.setCcNumber("1234");
		creditCard.setCcType("AX");
		creditCard.setExpiryDate("11/2020");
		order.setCreditCard(creditCard);
		
		return order;
	}
			
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
