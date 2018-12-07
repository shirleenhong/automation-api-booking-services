package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

import com.cwt.bpg.cbt.exchange.order.model.CarVendor;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CarVendorControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ExchangeOrderService eoService;

	@Mock
	private ExchangeOrderReportService eoReportService;

	@InjectMocks
	private CarVendorController controller;

	private String urlCarVendors;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		urlCarVendors = "/exchange-order/car-vendors";
	}

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	public void shouldGetCarVendors() throws Exception {

		List<CarVendor> carVendors = new ArrayList<>();

		when(eoService.getAllCarVendors()).thenReturn(carVendors);

		mockMvc.perform(get(urlCarVendors)).andExpect(status().isOk());

		verify(eoService, times(1)).getAllCarVendors();
	}
	
	@Test
	public void shouldSaveCarVendors() throws Exception {

		CarVendor carVendor = new CarVendor();
		carVendor.setCode("AB");
		carVendor.setCode("Car Test");

    	mockMvc.perform(put(urlCarVendors)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(carVendor)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

		verify(eoService, times(1)).saveCarVendor(any(CarVendor.class));
	}
	
	@Test
	public void shouldRemoveCarVendor() throws Exception {

		String code = "code";
		when(eoService.deleteCarVendor(code)).thenReturn(code);

        mockMvc.perform(delete(urlCarVendors+"/"+ code)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(eoService, times(1)).deleteCarVendor(anyString());
    }

	@Test
	public void shouldReturnCarVendorNotFoundWhenRecordDoesNotExist() throws Exception {

		String code = "code";
		when(eoService.deleteCarVendor(code)).thenReturn("");

        mockMvc.perform(delete(urlCarVendors+"/"+ code)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        verify(eoService, times(1)).deleteCarVendor(anyString());
    }
	
	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
