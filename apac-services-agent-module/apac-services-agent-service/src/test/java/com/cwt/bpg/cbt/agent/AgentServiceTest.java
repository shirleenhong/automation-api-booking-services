package com.cwt.bpg.cbt.agent;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
        when(repository.get(anyString())).thenReturn(expected);

        AgentInfo actual = service.getAgent("ABC123");

        verify(repository).get("ABC123");
        assertNotNull(actual);
    }

    @Test
    public void shouldReturnNullWhenAgentDoesNotExist() {
        AgentInfo expected = null;
        when(repository.get(anyString())).thenReturn(expected);

        AgentInfo actual = service.getAgent("ABC123");

        verify(repository).get("ABC123");
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
        String expected = "ABC123";
        when(repository.remove(anyString())).thenReturn(expected);

        String actual = service.delete(expected);

        verify(repository).remove(expected);
        assertThat(actual, is(equalTo(expected)));
    }
}
