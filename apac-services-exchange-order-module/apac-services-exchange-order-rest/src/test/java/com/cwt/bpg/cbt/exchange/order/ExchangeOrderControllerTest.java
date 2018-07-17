package com.cwt.bpg.cbt.exchange.order;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UriComponentsBuilder;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.Header;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
import com.cwt.bpg.cbt.exchange.order.validator.FopTypeValidator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ExchangeOrderControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ExchangeOrderService eoService;
	
	@Mock
	private ExchangeOrderReportService eoReportService;

	@Mock
	private FopTypeValidator fopTypeValidator;
	
	@InjectMocks
	private ExchangeOrderController controller;

	private String url;
	private String eoNumber;
	private String pnr;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		url = "/exchange-order";
		eoNumber = "1806100005";
		pnr = "U9L8VY";
	}

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void shouldReturnExchangeOrderNumber() throws Exception {

		ExchangeOrder order = createExchangeOrder();
		order.setEoNumber("1122334455");

		when(eoService.saveExchangeOrder(any(ExchangeOrder.class))).thenReturn(order);

		mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
				.andExpect(status().isOk()).andReturn().getResponse();

		verify(eoService, times(1)).saveExchangeOrder(any(ExchangeOrder.class));
	}

	@Test(expected = NestedServletException.class)
	public void shouldReturnBadRequestWhenFopTypeCXAndCreditCardNull() throws Exception {

		ExchangeOrder order = createExchangeOrder();
		order.setFopType("CX");
		order.setEoNumber("1122334455");
		order.setCreditCard(null);

		mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
				.andExpect(status().isBadRequest()).andReturn().getResponse();

		verifyZeroInteractions(eoService);
	}

	@Test(expected = NestedServletException.class)
	public void shouldHandleExchangeOrderNotFoundException() throws Exception {

		ExchangeOrder order = createExchangeOrder();

		when(eoService.saveExchangeOrder(anyObject()))
				.thenThrow(new ExchangeOrderNoContentException("eo number not found"));

		mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
				.andExpect(status().isNoContent()).andReturn().getResponse();

		verifyZeroInteractions(eoService);
	}

	@Test
	public void shouldGetExchangeOrderByExchangeOrderNumber() throws Exception {

		ExchangeOrder order = new ExchangeOrder();
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(order);

		mockMvc.perform(get(url + "/" + eoNumber)).andExpect(status().isOk());

		verify(eoService, times(1)).getExchangeOrder(eoNumber);
	}

    @Test
    public void shouldGetExchangeOrderByPNR() throws Exception {

        ExchangeOrder order = new ExchangeOrder();
        when(eoService.getExchangeOrderByRecordLocator(pnr)).thenReturn(Arrays.asList(order));

        mockMvc.perform(get(url + "/" + pnr)).andExpect(status().isOk());

        verify(eoService, times(1)).getExchangeOrderByRecordLocator(pnr);
    }

	@Test
	public void shouldNotReturnExchangeOrder() throws Exception {

		ExchangeOrder order = new ExchangeOrder();

		when(eoService.saveExchangeOrder(order)).thenReturn(order);

		mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
				.andExpect(status().isBadRequest()).andReturn().getResponse();

		verify(eoService, times(0)).saveExchangeOrder(any(ExchangeOrder.class));
	}

	private ExchangeOrder createExchangeOrder() {

		ExchangeOrder order = new ExchangeOrder();
		order.setFopType("CX");
		order.setDescription("test_description");
		order.setAdditionalInfoDate(Instant.now());
		order.setProductCode("PR01");
		Vendor vendor = new Vendor();
		vendor.setCode("VEN090909");
		order.setVendor(vendor);
		order.setFaxNumber("111");
		order.setAccountNumber("987654321");
		order.setPassengerName("Passenger");
		order.setAgentId("U001XXX");
		order.setPcc("SIN1234");
		order.setAgentName("Agent Name");
		order.setRecordLocator("PNR1234");
		order.setNettCost(new BigDecimal(0));
		order.setTotal(new BigDecimal(0));
		order.setEoAction("EO Action");
		order.setStatus("A");
		order.setRaiseCheque("Raise Cheque");

		order.setSellingPrice(new BigDecimal(0));
		order.setTotalSellingPrice(new BigDecimal(0));

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
		
		order.setEoNumber("1807200001");

		return order;
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	@Test
	public void shouldGeneratePdf() throws Exception {

		String exampleString = "example";
		byte[] pdfByte = exampleString.getBytes(StandardCharsets.UTF_8);
		when(eoReportService.generatePdf(Mockito.anyString())).thenReturn(pdfByte);

		MvcResult result = mockMvc.perform(get(url + "/pdf/" + eoNumber))
				.andExpect(status().isOk()).andReturn();

		String actualPdfName = result.getResponse().getHeaderValue("Content-Disposition").toString();

		assertTrue(actualPdfName.contains(eoNumber+".pdf"));
		verify(eoReportService, times(1)).generatePdf(Mockito.anyString());
	}


	@Test
	public void shouldEmailPdf() throws Exception {

		ExchangeOrder order = createExchangeOrder();

		when(eoReportService.emailPdf(order.getEoNumber())).thenReturn(new EmailResponse());

		mockMvc.perform(get(url+"/email/"+order.getEoNumber())
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(order)))
				.andExpect(status().isOk()).andReturn().getResponse();

		verify(eoReportService, times(1)).emailPdf(eq(order.getEoNumber()));	
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = NestedServletException.class)
	public void shouldGeneratePdfCheckedException() throws Exception {

		when(eoReportService.generatePdf(Mockito.anyString()))
				.thenThrow(ExchangeOrderException.class);

		mockMvc.perform(get(url + "/pdf/" + eoNumber))
				.andExpect(status().isNoContent()).andReturn();

        verifyZeroInteractions(eoService);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = NestedServletException.class)
	public void shouldGeneratePdfUnCheckedException() throws Exception {

		when(eoReportService.generatePdf(Mockito.anyString()))
				.thenThrow(Exception.class);

		mockMvc.perform(get(url + "/pdf/" + eoNumber))
				.andExpect(status().isInternalServerError()).andReturn();

        verifyZeroInteractions(eoService);
	}
	
	@Test
    public void shouldInvokeSearch() throws Exception {
        when(eoService.search(Mockito.any(ExchangeOrderSearchParam.class))).thenReturn(Collections.emptyList());

        final String uri = UriComponentsBuilder.newInstance().path("/exchange-orders")
        .queryParam("eoNumber", UUID.randomUUID().toString())
        .queryParam("vendorCode", UUID.randomUUID().toString())
        .queryParam("status", UUID.randomUUID().toString())
        .queryParam("recordLocator", UUID.randomUUID().toString())
        .queryParam("startCreationDate", "2008-05-29T00:00:00.000Z")
        .queryParam("endCreationDate", "2008-05-29T00:00:00.000Z")
        .build().toUriString();
        
        mockMvc.perform(get(uri)).andExpect(status().isOk());

        verify(eoService, times(1)).search(Mockito.any(ExchangeOrderSearchParam.class));
    }
	
}
