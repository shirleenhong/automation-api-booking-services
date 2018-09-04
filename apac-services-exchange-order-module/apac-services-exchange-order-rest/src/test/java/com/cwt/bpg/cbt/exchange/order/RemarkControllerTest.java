package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.exchange.order.OtherServiceFeesControllerNonAirFeeTest.convertObjectToJsonBytes;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.cwt.bpg.cbt.exchange.order.model.Remark;

import net.minidev.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class RemarkControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RemarkService service;

    @InjectMocks
    private RemarkController controller;

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
    public void getRemarksShouldReturnRemarks() throws Exception {
        mockMvc.perform(get("/remarks")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getAll();
    }
    
    @Test
    public void getRemarksByParamShouldReturnRemarks() throws Exception {
        mockMvc.perform(get("/remarks/countryCode01/productType01/remarkType01")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getRemarks("countryCode01", "productType01", "remarkType01");
    }

    @Test
    public void getRemarksByIdShouldReturnRemark() throws Exception {
        mockMvc.perform(get("/remarks/remarkId123")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        verify(service, times(1)).getRemark("remarkId123");
    }

    @Test
    public void putRemarkShouldSaveAndReturnSavedRemark() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("productType", "ProductType01");
        jsonObj.put("remarkType", "RemarkType01");
        jsonObj.put("countryCode", "CountryCode01");
        jsonObj.put("text", "XXX");

        mockMvc.perform(put("/remarks")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(jsonObj)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).save(any(Remark.class));
    }

    @Test
    public void removeRemarkShouldRemoveRemark() throws Exception {
        String remarkId = "remarkId123";
        when(service.delete(remarkId)).thenReturn(remarkId);

        mockMvc.perform(delete("/remarks/" + remarkId)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service, times(1)).delete("remarkId123");
    }

    @Test
    public void removeRemarkShouldReturnNotFoundWhenRecordDoesNotExist() throws Exception {
        String remarkId = "remarkId123";
        when(service.delete(remarkId)).thenReturn("");

        mockMvc.perform(delete("/remarks/" + remarkId)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        verify(service, times(1)).delete("remarkId123");
    }

}
