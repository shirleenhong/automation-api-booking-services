package com.cwt.bpg.cbt.security.repository;

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

import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.cwt.bpg.cbt.security.api.model.Token;

public class TokenRepositoryTest {

	@Mock
	private Datastore dataStore;
	
	@Mock
	private MorphiaComponent morphia;
	
	@InjectMocks
	private TokenRepository repository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shoulGetToken() {
		
		String token = "token";
		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(Token.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal(token)).thenReturn(query);
		Mockito.when(query.get()).thenReturn(new Token());
		
		Token result = repository.getToken(token);
		
		Mockito.verify(morphia, Mockito.times(1)).getDatastore();
		Mockito.verify(dataStore, Mockito.times(1)).createQuery(Token.class);
		
		assertNotNull(result);
	}

}
