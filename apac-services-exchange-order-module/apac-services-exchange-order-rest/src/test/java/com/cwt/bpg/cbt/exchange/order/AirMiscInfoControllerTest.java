package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AirMiscInfoControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ExchangeOrderService eoService;

	@Mock
	private ExchangeOrderReportService eoReportService;

	@InjectMocks
	private AirMiscInfoController controller;

	private String urlAirMiscInfo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		urlAirMiscInfo = "/exchange-order/air-misc-info";
	}

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	public void shouldGetAirMiscInfo() throws Exception {

		List<AirMiscInfo> airMiscInfoList = new ArrayList<>();
		String clientAccountNumber = "12345";

		List<String> reportingFieldTypeIds = new ArrayList<>();
		reportingFieldTypeIds.add("5");

		when(eoService.getAirMiscInfos(clientAccountNumber, reportingFieldTypeIds))
				.thenReturn(airMiscInfoList);

		mockMvc.perform(get(
				urlAirMiscInfo + "/" + clientAccountNumber + "?reportingFieldTypeIds=5"))
				.andExpect(status().isOk());

		verify(eoService, times(1)).getAirMiscInfos(clientAccountNumber,
				reportingFieldTypeIds);
	}
	
	@Test
	public void shouldSaveAirMiscInfo() throws Exception {

    	mockMvc.perform(put(urlAirMiscInfo)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(new AirMiscInfo())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

		verify(eoService, times(1)).saveAirMiscInfo(any(AirMiscInfo.class));
	}
	
	@Test
	public void shouldRemoveAirMiscInfo() throws Exception {

		String id = "12345";
		when(eoService.deleteAirMiscInfo(id)).thenReturn(id);

        mockMvc.perform(delete(urlAirMiscInfo+"/"+ id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(eoService, times(1)).deleteAirMiscInfo(anyString());
    }

	@Test
	public void shouldReturnAirMiscInfoNotFoundWhenRecordDoesNotExist() throws Exception {

		String id = "12345";
		when(eoService.deleteAirMiscInfo(id)).thenReturn("");

        mockMvc.perform(delete(urlAirMiscInfo+"/"+ id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        verify(eoService, times(1)).deleteAirMiscInfo(anyString());
    }
	
	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
