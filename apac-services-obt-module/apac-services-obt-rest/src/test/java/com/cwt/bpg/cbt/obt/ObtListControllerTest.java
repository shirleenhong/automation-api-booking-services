package com.cwt.bpg.cbt.obt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

import com.cwt.bpg.cbt.obt.model.ObtList;
import com.cwt.bpg.cbt.obt.model.OnlineBookingTool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ObtListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ObtListService service;

    @InjectMocks
    private ObtListController controller;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getObtListShouldReturnObtList() throws Exception {
		ObtList obtList = new ObtList();
		obtList.setCountryCode("IN");

        when(service.get(obtList.getCountryCode())).thenReturn(obtList);

        mockMvc.perform(get("/obt-list/IN")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(obtList)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).get(obtList.getCountryCode());
    }

	@Test
	public void getObtListShouldReturnObtList_for_unmatch_case() throws Exception {
		ObtList obtList = new ObtList();
		obtList.setCountryCode("IN");

		when(service.get(obtList.getCountryCode())).thenReturn(obtList);

		mockMvc.perform(get("/obt-list/in")
				.contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(obtList)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();

		verify(service, times(1)).get(obtList.getCountryCode());
	}

    @Test
    public void putObtListShouldSaveAndReturnSavedObtList() throws Exception {
		ObtList obtList = new ObtList();
		obtList.setCountryCode("IN");

		OnlineBookingTool obt = new OnlineBookingTool();
		obt.setOnlineUnassistedCode("EBA");
		obt.setName("Concur");
		obt.setAgentAssistedCode("AMA");

		when(service.save(any(ObtList.class))).thenReturn(obtList);

        mockMvc.perform(put("/obt-list")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(obtList)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any(ObtList.class));
    }

    @Test
    public void removeObtListShouldRemoveObtList() throws Exception {
		String countryCode = "IN";

		when(service.delete(countryCode)).thenReturn(countryCode);

        mockMvc.perform(delete("/obt-list/IN")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).delete(countryCode);
    }

	@Test
	public void removeObtListShouldRemoveObtListForUnmatchedCase() throws Exception {
		String countryCode = "IN";

		when(service.delete(countryCode)).thenReturn(countryCode);

		mockMvc.perform(delete("/obt-list/" + countryCode.toLowerCase())
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();

		verify(service, times(1)).delete(countryCode);
	}

	@Test
	public void removeObtListShouldReturnNoFoundWhenRecordDoesNotExist() throws Exception {
		String countryCode = "IN";

		when(service.delete(countryCode)).thenReturn("");

		mockMvc.perform(delete("/obt-list/" + countryCode.toLowerCase())
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andReturn()
				.getResponse();

		verify(service, times(1)).delete(countryCode);
	}

	static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
