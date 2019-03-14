package com.cwt.bpg.cbt.agent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
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
	public void shouldReturnResultWhenAgentUIDIsNotNull() {
//		AgentInfo expected = new AgentInfo();
//        when(query.get()).thenReturn(expected);
//        when(query.field(anyString())).thenReturn(mock(FieldEnd.class));
//
//        AgentInfo actual = repository.get("ABC123", "sg");
//
//        InOrder inOrder = inOrder(morphia, datastore, query);
//        inOrder.verify(morphia).getDatastore();
//        inOrder.verify(datastore).createQuery(AgentInfo.class);
//        inOrder.verify(query).field(anyString());
//        inOrder.verify(query).field(anyString());
//        
//		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnResultWhenAgentUIDIsNull() {
//		AgentInfo expected = new AgentInfo();
//		when(query.get()).thenReturn(expected);
//		when(query.field(anyString())).thenReturn(mock(FieldEnd.class));
//
//		AgentInfo actual = repository.get("");
//
//		InOrder inOrder = inOrder(morphia, datastore, query);
//		inOrder.verify(morphia).getDatastore();
//		inOrder.verify(datastore).createQuery(AgentInfo.class);
//		inOrder.verify(query).field(anyString());
//		inOrder.verify(query).field(anyString());
//
//		assertEquals(expected, actual);
	}

}
