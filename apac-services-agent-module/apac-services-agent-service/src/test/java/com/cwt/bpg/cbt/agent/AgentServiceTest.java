package com.cwt.bpg.cbt.agent;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.agent.model.AgentInfo;


public class AgentServiceTest {
	
	@InjectMocks
	private AgentService service;
	
	@Mock
	private AgentRepository repository;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void shouldReturnExistingAgent() {
        AgentInfo expected = new AgentInfo();
        when(repository.getAgent(anyString(), anyString())).thenReturn(expected);

        AgentInfo actual = service.getAgent("ABC123", "sg");

        verify(repository).getAgent("ABC123", "sg");
        assertNotNull(actual);
    }

    @Test
    public void shouldReturnNullWhenAgentDoesNotExist() {
        AgentInfo expected = null;
        when(repository.getAgent(anyString(), anyString())).thenReturn(expected);

        AgentInfo actual = service.getAgent("ABC123", "sg");

        verify(repository).getAgent("ABC123", "sg");
        assertNull(actual);
    }

    @Test
    public void shouldGetAgents() {
        List<AgentInfo> expected = new ArrayList<>();
        when(repository.getAll()).thenReturn(expected);

        List<AgentInfo> actual = service.getAgents();

        verify(repository).getAll();
        assertEquals(actual, expected);
    }

    @Test
    public void shouldSave() {
        AgentInfo expected = mock(AgentInfo.class);
        when(repository.put(any(AgentInfo.class))).thenReturn(expected);

        AgentInfo savedAgentInfo = service.save(expected);

        verify(repository).put(expected);
        assertThat(savedAgentInfo, is(equalTo(expected)));
    }

    @Test
    public void shouldDelete() {
        String expected = "5b2870d6284b8d1ac84300ad";
        when(repository.remove(new ObjectId(expected))).thenReturn(expected);

        String actual = service.remove(expected);

        verify(repository).remove(new ObjectId(expected));
        assertThat(actual, is(equalTo(expected)));
    }
}
