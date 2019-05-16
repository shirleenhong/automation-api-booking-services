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

import com.cwt.bpg.cbt.exchange.order.model.RoomType;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class HotelRoomTypesControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ExchangeOrderService eoService;

	@Mock
	private ExchangeOrderReportService eoReportService;

	@InjectMocks
	private HotelRoomTypesController controller;

	private String urlRoomTypes;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		urlRoomTypes = "/exchange-order/room-types";
	}

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
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
	
	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
