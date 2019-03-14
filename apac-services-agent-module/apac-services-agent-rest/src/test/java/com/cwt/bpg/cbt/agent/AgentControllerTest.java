package com.cwt.bpg.cbt.agent;

import static org.mockito.Matchers.any;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwt.bpg.cbt.agent.model.AgentInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AgentControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AgentService service;

	@InjectMocks
	private AgentController controller;

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	
	@Test
    public void getAgentsReturns200() throws Exception {
        List<AgentInfo> agents = new ArrayList<>();
        when(service.getAgents()).thenReturn(agents);

        mockMvc.perform(get("/agent")
            .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(agents)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).getAgents();
    }
	
	@Test
    public void getAgentReturns200WhenAgentExists() throws Exception {
        AgentInfo agent = new AgentInfo();
        when(service.getAgent("ABC123", "sg")).thenReturn(agent);

        mockMvc.perform(get("/agent/ABC123")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(agent)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).getAgent("ABC123", "sg");
    }
	
	@Test
    public void getAgentReturns204WhenAgentDoesNotExist() throws Exception {
        when(service.getAgent("CBA321", "sg")).thenReturn(null);

        mockMvc.perform(get("/agent/CBA321")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();

        verify(service).getAgent("CBA321", "sg");
    }
	
	
	@Test
    public void putAgentReturns200() throws Exception {
        AgentInfo agent = new AgentInfo();
        agent.setUid("ABC123");
        agent.setPhone("123456789");
        agent.setCountryCode("PH");
        
        when(service.save(any(AgentInfo.class))).thenReturn(agent);

        mockMvc.perform(put("/agent")
                .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(agent)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).save(any(AgentInfo.class));
    }

    @Test
    public void removeAgentReturns200UponSuccessfulDeletion() throws Exception {
        when(service.delete("ABC123", "sg")).thenReturn("ABC123");

        mockMvc.perform(delete("/agent/ABC123")
                .contentType(APPLICATION_JSON_UTF8).content("ABC123"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).delete("ABC123", "sg");
    }

    @Test
    public void removeAgentReturns404WhenThereIsNoRecordToDelete() throws Exception {
        when(service.delete("ABC123", "sg")).thenReturn("");

        mockMvc.perform(delete("/agent/ABC123")
                .contentType(APPLICATION_JSON_UTF8).content(""))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        verify(service).delete("ABC123", "sg");
    }
	
	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
