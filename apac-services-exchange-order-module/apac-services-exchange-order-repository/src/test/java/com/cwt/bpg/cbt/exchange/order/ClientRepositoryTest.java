package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class ClientRepositoryTest {
	
	@Mock
	private Datastore dataStore;
	
	@Mock
	private MorphiaComponent morphia;


	@InjectMocks
	private ClientRepository repo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canGetClient() {
		Query<Client> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(Client.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal("profileName")).thenReturn(query);
		Mockito.when(query.get()).thenReturn(new Client());
		
		
		Client result = repo.getClient("profileName");
		
		Mockito.verify(morphia, Mockito.times(1)).getDatastore();
		Mockito.verify(dataStore, Mockito.times(1)).createQuery(Client.class);
		
		assertNotNull(result);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canGetDefaultClient() {
		Query<Client> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(Client.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal(-1)).thenReturn(query);
		Mockito.when(query.get()).thenReturn(new Client());
				
		Client result = repo.getClient(-1);
		
		Mockito.verify(morphia, Mockito.times(1)).getDatastore();
		Mockito.verify(dataStore, Mockito.times(1)).createQuery(Client.class);
		
		assertNotNull(result);
	}

}