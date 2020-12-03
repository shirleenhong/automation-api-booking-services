package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

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
		when(morphia.getDatastore()).thenReturn(dataStore);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canGetClient() {
		Query<Client> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        String clientAccountNumber = "12345678";
        when(dataStore.createQuery(Client.class)).thenReturn(query);
        when(query.field(anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);
		when(query.get()).thenReturn(new Client());

		Client result = repo.getClient(clientAccountNumber);
		
		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(Client.class);
		
		assertNotNull(result);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void canGetDefaultClient() {
		Query<Client> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		when(dataStore.createQuery(Client.class)).thenReturn(query);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal(-1)).thenReturn(query);
		when(query.get()).thenReturn(new Client());
				
		Client result = repo.get(-1);
		
		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(Client.class);
		
		assertNotNull(result);
	}

	@Test
	public void canDeleteClient() {
        Query<Client> query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        String clientAccountNumber = "12345678";
        WriteResult deleteResult = new WriteResult(1, false, clientAccountNumber);
        when(dataStore.createQuery(Client.class)).thenReturn(query);
        when(dataStore.delete(query)).thenReturn(deleteResult);
        when(query.field(anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(clientAccountNumber)).thenReturn(query);

        String result = repo.remove(clientAccountNumber);

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(Client.class);
        verify(dataStore, times(1)).delete(query);

        assertThat(result, is(equalTo(clientAccountNumber)));
	}

	@Test
	public void shouldDeleteNothingWhenClientDoesNotExist() {
		Query<Client> query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		String clientAccountNumber = "12345678";
		WriteResult deleteResult = new WriteResult(0, false, clientAccountNumber);
		when(dataStore.createQuery(Client.class)).thenReturn(query);
		when(dataStore.delete(query)).thenReturn(deleteResult);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal(clientAccountNumber)).thenReturn(query);

		String result = repo.remove(clientAccountNumber);

		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(Client.class);
		verify(dataStore, times(1)).delete(query);

		assertThat(result, emptyOrNullString());
	}

	@Test
	public void shouldCreateClientRepository() {
		ClientRepository repo = new ClientRepository();

		repo.identity((i) -> {
			assertThat(i[0], is(equalTo(Client.class)));
			assertThat(i[1], is(equalTo("clientAccountNumber")));
		});
	}
}
