package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.exchange.order.OtherServiceFeesControllerNonAirFeeTest.convertObjectToJsonBytes;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;
import net.minidev.json.JSONObject;

public class ReportHeaderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportHeaderService service;

    @InjectMocks
    private ReportHeaderController controller;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
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
    public void getReportHeaderByCodeShouldReturnReportHeaders() throws Exception {
        mockMvc.perform(get("/report-headers/XXX")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getHeaderReport("XXX");
    }

    @Test
    public void putReportHeaderShouldSaveAndReturnSavedReportHeader() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("countryCode", "SG");
        jsonObj.put("companyName", "Test Carlson Wagonlit Travel Singapore, Inc.");
        jsonObj.put("address", "Test Address Singapore");
        jsonObj.put("phoneNumber", "0987654321");
        jsonObj.put("faxNumber", "123456789");


        mockMvc.perform(put("/report-headers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any(ReportHeader.class));
    }

    @Test
    public void putReportHeaderShouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("companyName", "Test Carlson Wagonlit Travel Singapore, Inc.");
        jsonObj.put("address", "Test Address Singapore");
        jsonObj.put("phoneNumber", "0987654321");
        jsonObj.put("faxNumber", "123456789");

        mockMvc.perform(put("/report-headers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void removeReportHeaderShouldDeleteReportHeader() throws Exception {

        String countryCode = "XX";
        when(service.delete(countryCode)).thenReturn(countryCode);

        mockMvc.perform(delete("/report-headers/" + countryCode)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        verify(service, times(1)).delete(anyString());
    }

    @Test
    public void removeReportHeaderShouldReturnNotFoundWhenRecordDoesNotExist() throws Exception {

        String countryCode = "XX";
        when(service.delete(countryCode)).thenReturn("");

        mockMvc.perform(delete("/report-headers/" + countryCode)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(service, times(1)).delete(anyString());
    }

    @Test
    public void removeAirportShouldReturnBadRequestOnEmptyMandatoryField() throws Exception {
        mockMvc.perform(delete("/report-headers")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verifyZeroInteractions(service);
    }
}
