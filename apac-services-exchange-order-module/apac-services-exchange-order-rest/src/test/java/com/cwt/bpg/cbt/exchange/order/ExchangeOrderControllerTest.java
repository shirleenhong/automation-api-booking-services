package com.cwt.bpg.cbt.exchange.order;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

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

import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaVendor;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
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

	@InjectMocks
	private ExchangeOrderController controller;

	private String url;
	private String urlSg;
	private String urlIn;
	private String eoNumber;
	private String pnr;
	private String urlRoomTypes;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		url = "/exchange-order";
		urlSg = "/exchange-order/sg";
		urlIn = "/exchange-order/in";
		eoNumber = "1806100005";
		pnr = "U9L8VY";
		urlRoomTypes = "/exchange-order/room-types";
	}

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void shouldReturnExchangeOrderNumber() throws Exception {

		ExchangeOrder order = createExchangeOrder();
		order.getServiceInfo().setCommission(BigDecimal.ZERO);
		order.getServiceInfo().setGst(BigDecimal.ZERO);
		order.getServiceInfo().setMerchantFee(BigDecimal.ZERO);
		order.setEoNumber("1122334455");

		when(eoService.save(anyString(), any(ExchangeOrder.class))).thenReturn(order);

        mockMvc.perform(post(urlSg).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

		verify(eoService, times(1)).save(anyString(), any(ExchangeOrder.class));
	}

	@Test
	public void shouldCreateExchangeOrder() throws Exception {

		ExchangeOrder order = createExchangeOrder();
		order.getServiceInfo().setCommission(BigDecimal.ZERO);
		order.getServiceInfo().setGst(BigDecimal.ZERO);
		order.getServiceInfo().setMerchantFee(BigDecimal.ZERO);
		order.setEoNumber(null);

		when(eoService.save(anyString(), any(ExchangeOrder.class))).thenReturn(order);

        mockMvc.perform(post(urlSg).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

		verify(eoService, times(1)).save(anyString(), any(ExchangeOrder.class));
	}

	@Test
	public void shouldCreateIndiaExchangeOrder() throws Exception {

		IndiaExchangeOrder order = new IndiaExchangeOrder();
		order.setEoNumber(null);
		order.setCountryCode("IN");
		order.setProductCode("PR01");
		IndiaVendor vendor = new IndiaVendor();
		vendor.setCode("VEN090909");
		order.setVendor(vendor);

		when(eoService.save(anyString(), any(ExchangeOrder.class))).thenReturn(order);

        mockMvc.perform(post(urlIn).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

		verify(eoService, times(1)).save(anyString(), any(IndiaExchangeOrder.class));
	}

	@Test
	public void shouldUpdateExchangeOrder() throws Exception {

		ExchangeOrder order = createExchangeOrder();
		order.getServiceInfo().setCommission(BigDecimal.ZERO);
		order.getServiceInfo().setGst(BigDecimal.ZERO);
		order.getServiceInfo().setMerchantFee(BigDecimal.ZERO);
		order.getServiceInfo().getFormOfPayment().setFopType(FopType.CWT);
		order.setEoNumber("1122334455");

        mockMvc.perform(post(urlSg).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

		verify(eoService, times(1)).save(anyString(), any(ExchangeOrder.class));
	}

    @Test
    public void shouldUpdateFinanceFields() throws Exception {
        when(eoService.update(any(ExchangeOrder.class))).thenReturn(true);

        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(new ExchangeOrder())))
                .andExpect(status().isOk()).andReturn().getResponse();

        verify(eoService, times(1)).update(any(ExchangeOrder.class));
    }

    @Test(expected = NestedServletException.class)
	public void shouldReturnBadRequestWhenFopTypeCXAndCreditCardNull() throws Exception {

		ExchangeOrder order = createExchangeOrder();
		order.getServiceInfo().setCommission(BigDecimal.ZERO);
		order.getServiceInfo().setGst(BigDecimal.ZERO);
		order.getServiceInfo().setMerchantFee(BigDecimal.ZERO);
		order.getServiceInfo().getFormOfPayment().setFopType(FopType.CWT);
		order.setEoNumber(null);
		order.getServiceInfo().getFormOfPayment().setCreditCard(null);

        mockMvc.perform(post(urlSg).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

		verifyZeroInteractions(eoService);
	}

	@Test(expected = NestedServletException.class)
	public void shouldHandleExchangeOrderNotFoundException() throws Exception {

		ExchangeOrder order = createExchangeOrder();
		order.getServiceInfo().setCommission(BigDecimal.ZERO);
		order.getServiceInfo().setGst(BigDecimal.ZERO);
		order.getServiceInfo().setMerchantFee(BigDecimal.ZERO);
		when(eoService.save(anyString(), any(ExchangeOrder.class)))
				.thenThrow(new ExchangeOrderNoContentException("eo number not found"));

        mockMvc.perform(post(urlSg).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();

		verifyZeroInteractions(eoService);
	}

	@Test
	public void shouldGetExchangeOrderByExchangeOrderNumber() throws Exception {

		ExchangeOrder order = new ExchangeOrder();
		when(eoService.get(eoNumber)).thenReturn(order);

		mockMvc.perform(get(urlSg + "/" + eoNumber)).andExpect(status().isOk());

		verify(eoService, times(1)).get("sg", eoNumber);
	}

	@Test
	public void shouldGetExchangeOrderByPNR() throws Exception {
		List orders = Arrays.asList(new ExchangeOrder());
		when(eoService.getExchangeOrderByRecordLocator("IN", pnr)).thenReturn(orders);

		mockMvc.perform(get(urlSg + "/" + pnr)).andExpect(status().isOk());

		verify(eoService, times(1)).getExchangeOrderByRecordLocator("sg", pnr);
	}

	@Test
	public void shouldGetIndiaExchangeOrderByExchangeOrderNumber() throws Exception {

		ExchangeOrder order = new ExchangeOrder();
		when(eoService.get(eoNumber)).thenReturn(order);

		mockMvc.perform(get(urlIn + "/" + eoNumber)).andExpect(status().isOk());

		verify(eoService, times(1)).get("IN", eoNumber);
	}

	@Test
	public void shouldGetIndiaExchangeOrderByPNR() throws Exception {
		List orders = Arrays.asList(new ExchangeOrder());
		when(eoService.getExchangeOrderByRecordLocator("IN", pnr)).thenReturn(orders);

		mockMvc.perform(get(urlIn + "/" + pnr)).andExpect(status().isOk());

		verify(eoService, times(1)).getExchangeOrderByRecordLocator("IN", pnr);
	}

	@Test
	public void shouldNotReturnExchangeOrder() throws Exception {

		ExchangeOrder order = new ExchangeOrder();

		when(eoService.save("sg", order)).thenReturn(order);

        mockMvc.perform(post(urlSg).contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(order)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

		verify(eoService, times(0)).save(anyString(), any(ExchangeOrder.class));
	}

	private ExchangeOrder createExchangeOrder() {

		ExchangeOrder order = new ExchangeOrder();
		order.setServiceInfo(new ServiceInfo());
		order.getServiceInfo().setFormOfPayment(new FormOfPayment());
		order.getServiceInfo().setAdditionalInfo(new AdditionalInfo());
		order.getServiceInfo().getFormOfPayment().setFopType(FopType.CWT);
		order.getServiceInfo().getAdditionalInfo().setDescription("test_description");
		order.getServiceInfo().getAdditionalInfo().setDate(Instant.now());
		order.setProductCode("PR01");
		Vendor vendor = new Vendor();
		vendor.setCode("VEN090909");
		order.setVendor(vendor);
		order.setAccountNumber("987654321");
		order.setPassengerName("Passenger");
		order.setAgentId("U001XXX");
		order.setPcc("SIN1234");
		order.setAgentName("Agent Name");
		order.setRecordLocator("PNR1234");
		order.getServiceInfo().setNettCost(new BigDecimal(0));
		order.setTotal(new BigDecimal(0));
		order.setEoAction(EoAction.EMAIL);
		order.setStatus(EoStatus.COMPLETED);
		order.setRaiseCheque("Raise Cheque");

		order.getServiceInfo().setSellingPrice(new BigDecimal(0));
		order.getServiceInfo().setTotalSellingPrice(new BigDecimal(0));

		CreditCard creditCard = new CreditCard();
		creditCard.setCcNumber("1234");
		creditCard.setCcType("AX");
		creditCard.setExpiryDate("11/2020");
		order.getServiceInfo().getFormOfPayment().setCreditCard(creditCard);

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
		when(eoReportService.generatePdf(anyString())).thenReturn(pdfByte);

        MvcResult result = mockMvc.perform(get(url + "/pdf/" + eoNumber))
                .andExpect(status().isOk())
                .andReturn();

		String actualPdfName = result.getResponse().getHeaderValue("Content-Disposition").toString();

		assertTrue(actualPdfName.contains(eoNumber + ".pdf"));
		verify(eoReportService, times(1)).generatePdf(anyString());
	}

	@Test
	public void shouldEmailPdf() throws Exception {

		ExchangeOrder order = createExchangeOrder();

		when(eoReportService.emailPdf(order.getEoNumber())).thenReturn(new EmailResponse());

        mockMvc.perform(get(url + "/email/" + order.getEoNumber())
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(order)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

		verify(eoReportService, times(1)).emailPdf(eq(order.getEoNumber()));
	}

	@SuppressWarnings("unchecked")
	@Test(expected = NestedServletException.class)
	public void shouldGeneratePdfCheckedException() throws Exception {

		when(eoReportService.generatePdf(anyString())).thenThrow(ApiServiceException.class);

        mockMvc.perform(get(url + "/pdf/" + eoNumber))
                .andExpect(status().isNoContent())
                .andReturn();

		verifyZeroInteractions(eoService);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = NestedServletException.class)
	public void shouldGeneratePdfUnCheckedException() throws Exception {

		when(eoReportService.generatePdf(anyString())).thenThrow(Exception.class);

        mockMvc.perform(get(url + "/pdf/" + eoNumber))
                .andExpect(status().isInternalServerError())
                .andReturn();

		verifyZeroInteractions(eoService);
	}

	@Test
	public void shouldInvokeSearch() throws Exception {
		when(eoService.search(Mockito.any(ExchangeOrderSearchParam.class))).thenReturn(Collections.emptyList());

		final String uri = UriComponentsBuilder.newInstance().path("/exchange-orders")
				.queryParam("eoNumber", UUID.randomUUID().toString())
				.queryParam("vendorCode", UUID.randomUUID().toString())
				.queryParam("countryCode", UUID.randomUUID().toString())
				.queryParam("raiseType", UUID.randomUUID().toString()).queryParam("status", EoStatus.NEW)
				.queryParam("recordLocator", UUID.randomUUID().toString())
				.queryParam("startCreationDate", "2008-05-29T00:00:00.000Z")
				.queryParam("endCreationDate", "2008-05-29T00:00:00.000Z").build().toUriString();

		mockMvc.perform(get(uri)).andExpect(status().isOk());

		verify(eoService, times(1)).search(Mockito.any(ExchangeOrderSearchParam.class));
	}

	@Test
	public void shouldGetRoomTypes() throws Exception {

		List<RoomType> roomTypeList = new ArrayList<>();

		when(eoService.getAll()).thenReturn(roomTypeList);

		mockMvc.perform(get(urlRoomTypes)).andExpect(status().isOk());

		verify(eoService, times(1)).getAll();
	}

	@Test
	public void shouldSaveRoomType() throws Exception {

		RoomType roomType = new RoomType();
		roomType.setCode("A");
		roomType.setCode("Test");

    	mockMvc.perform(put(urlRoomTypes)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(roomType)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

		verify(eoService, times(1)).save(any(RoomType.class));
	}

	@Test
	public void shouldRemoveRoomTypes() throws Exception {

		String code = "code";
		when(eoService.delete(code)).thenReturn(code);

        mockMvc.perform(delete(urlRoomTypes+"/"+ code)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(eoService, times(1)).delete(anyString());
    }

	@Test
	public void shouldReturnNotFoundWhenRecordDoesNotExist() throws Exception {

		String code = "code";
		when(eoService.delete(code)).thenReturn("");

        mockMvc.perform(delete(urlRoomTypes+"/"+ code)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        verify(eoService, times(1)).delete(anyString());
    }

}
