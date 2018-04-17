package com.cwt.bpg.cbt.security.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.cwt.bpg.cbt.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.mongodb.config.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.security.api.TokenApi;
import com.cwt.bpg.cbt.security.api.model.Token;
import com.cwt.bpg.cbt.security.api.model.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@RunWith(MockitoJUnitRunner.class)
public class TokenImplTest {

	
	@Mock
	private MongoDbConnection mongoDbConnection;

	@Mock
	private DBObjectMapper dBObjectMapper;
	
	@InjectMocks
	private TokenApi tokenApi = new TokenImpl();
	
	@SuppressWarnings("rawtypes")
	@Test
	public void shouldExist() throws IOException {
		
		FindIterable iterable = Mockito.mock(FindIterable.class);
		MongoCollection collection = Mockito.mock(MongoCollection.class);
		Document doc = Mockito.mock(Document.class);
		Mockito.when(mongoDbConnection.getCollection(Mockito.anyString())).thenReturn(collection);
		Mockito.when(collection.find(Mockito.any(Document.class))).thenReturn(iterable);
		Mockito.when(iterable.first()).thenReturn(doc);
		
		Token tokenResult = Mockito.mock(Token.class);
		tokenResult.setKey("t0K3nK3y");
		User user = new User();
		user.setUsername("JR");
		
		Mockito.when(dBObjectMapper.mapDocumentToBean(doc,Token.class)).thenReturn(tokenResult);
		
		
		assertTrue(tokenApi.isTokenExist("tokenkey"));
	}
	
	@Test
	public void shouldNotExist() throws IOException {
		
		FindIterable iterable = Mockito.mock(FindIterable.class);
		MongoCollection collection = Mockito.mock(MongoCollection.class);
		Document doc = Mockito.mock(Document.class);
		Mockito.when(mongoDbConnection.getCollection(Mockito.anyString())).thenReturn(collection);
		Mockito.when(collection.find(Mockito.any(Document.class))).thenReturn(iterable);
		Mockito.when(iterable.first()).thenReturn(doc);
		
		
		Mockito.when(dBObjectMapper.mapDocumentToBean(doc,Token.class)).thenReturn(null);
		
		
		assertFalse(tokenApi.isTokenExist("tokenkey"));
	}

	
}
