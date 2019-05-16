package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

import com.cwt.bpg.cbt.exchange.order.model.VmpdReasonCode;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class VmpdReasonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExchangeOrderService eoService;

    @Mock
    private ExchangeOrderReportService eoReportService;

    @InjectMocks
    private VmpdReasonController controller;

    private String urlVmpdReason;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        urlVmpdReason = "/exchange-order/vmpd";
    }

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void shouldGetVmpdReason() throws Exception {

        List<VmpdReasonCode> vmpdReasonCodes = new ArrayList<>();
        when(eoService.getAllVmpdReasonCodes()).thenReturn(vmpdReasonCodes);

        mockMvc.perform(get(urlVmpdReason)).andExpect(status().isOk());

        verify(eoService, times(1)).getAllVmpdReasonCodes();
    }

    @Test
    public void shouldSaveVmpdReason() throws Exception {

        mockMvc.perform(put(urlVmpdReason).contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(new VmpdReasonCode())))
                .andExpect(status().isOk()).andReturn().getResponse();

        verify(eoService, times(1)).saveVmpdReasonCode(any(VmpdReasonCode.class));
    }

    @Test
    public void shouldRemoveVmpdReason() throws Exception {

        String code = "12345";
        when(eoService.deleteVmpdReasonCode(code)).thenReturn(code);

        mockMvc.perform(
                delete(urlVmpdReason + "/" + code).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse();
        verify(eoService, times(1)).deleteVmpdReasonCode(anyString());
    }

    @Test
    public void shouldReturnVmpdReasonNotFoundWhenRecordDoesNotExist() throws Exception {

        String code = "12345";
        when(eoService.deleteVmpdReasonCode(code)).thenReturn("");

        mockMvc.perform(
                delete(urlVmpdReason + "/" + code).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound()).andReturn().getResponse();
        verify(eoService, times(1)).deleteVmpdReasonCode(anyString());
    }

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
