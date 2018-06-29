package com.cwt.bpg.cbt.exchange.order;

<<<<<<< HEAD
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.Instant;

=======
import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Header;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
>>>>>>> master
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

<<<<<<< HEAD
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Header;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
=======
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
>>>>>>> master

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
	public void shouldHandleExchangeOrderNotFoundException() throws Exception {
						
		ExchangeOrder order = createExchangeOrder();
		
		when(eoService.saveExchangeOrder(anyObject())).thenThrow(new ExchangeOrderException("eo number not found"));

		mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(order)))
				.andExpect(status().isNoContent())
				.andReturn()
				.getResponse();

		verify(eoService,times(1)).saveExchangeOrder(any(ExchangeOrder.class));
	}
	
	@Test
	public void shouldReturnExchangeOrderNumber() throws Exception {
						
		ExchangeOrder order = createExchangeOrder();
		
		when(eoService.saveExchangeOrder(order)).thenReturn(order);

		mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(order)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();

		verify(eoService,times(1)).saveExchangeOrder(any(ExchangeOrder.class));
	}
	
	@Test
	public void shouldNotReturnExchangeOrder() throws Exception {
						
		ExchangeOrder order = new ExchangeOrder();
		
		when(eoService.saveExchangeOrder(order)).thenReturn(order);

		mockMvc.perform(post(url)
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(order)))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse();

		verify(eoService,times(0)).saveExchangeOrder(any(ExchangeOrder.class));
	}
	
	@Test
	public void shouldGetExchangeOrderNumber() throws Exception {

		String eoNumber = "1806100005";
		ExchangeOrder order = new ExchangeOrder();
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(order);
		
		mockMvc.perform(get(url + "/" + eoNumber))
				.andExpect(status().isOk());
	
		verify(eoService, times(1)).getExchangeOrder(eoNumber);
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
		order.setAgentName("Agent Name");
		order.setRecordLocator("Record Locator");
		order.setNettCost(new BigDecimal(0));
		order.setTotal(new BigDecimal(0));
		order.setEoAddress("EO Address");
		order.setStatus("A");
		order.setRaiseCheque("Raise Cheque");
		
		Header header = new Header();
		header.setAddress("Header Address");
		header.setPhoneNumber("02 4595900");
		header.setFaxNumber("02 4595900");
		
		order.setHeader(header);
		
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

	@Test
	public void shouldGeneratePdf() throws Exception {

		String eoNumber = "1806100005";

		String exampleString = "example";
		InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

		when(eoService.generatePdf(eoNumber)).thenReturn(stream);

		mockMvc.perform(get(url + "/generatePdf/" + eoNumber))
				.andExpect(status().isOk());

		verify(eoService, times(1)).generatePdf(Mockito.anyString());
	}
}
