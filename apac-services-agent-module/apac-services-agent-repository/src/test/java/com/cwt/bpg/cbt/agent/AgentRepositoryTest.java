package com.cwt.bpg.cbt.agent;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.agent.model.AgentInfo;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class AgentRepositoryTest {

	@InjectMocks
	private AgentRepository repository;

	@Mock
	private MorphiaComponent morphia;

	@Mock
	private Datastore datastore;

	@Mock
	private Query<AgentInfo> query;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		when(datastore.createQuery(AgentInfo.class)).thenReturn(query);
		when(morphia.getDatastore()).thenReturn(datastore);
	}

	@Test
	public void shouldReturnResultWhenUidAndCountryCodeAreNotNull() {
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(query);
		when(query.get()).thenReturn(new AgentInfo());
		
		repository.getAgent("123","SG");

		verify(query, times(2)).field(anyString());
        verify(morphia, times(1)).getDatastore();
	}
}
