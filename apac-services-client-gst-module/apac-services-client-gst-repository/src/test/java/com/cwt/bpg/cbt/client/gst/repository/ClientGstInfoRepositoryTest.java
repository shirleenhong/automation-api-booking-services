package com.cwt.bpg.cbt.client.gst.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@RunWith(MockitoJUnitRunner.class)
public class ClientGstInfoRepositoryTest {	
	
	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;
	
	@Mock
	private DB db;
	
	@Mock
	private DBCollection dbCollection;

	@InjectMocks
	private ClientGstInfoRepository repository;

	@Before
	public void init() {
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
		Mockito.when(dataStore.getCollection(ClientGstInfo.class)).thenReturn(dbCollection);
		Mockito.when(dataStore.getDB()).thenReturn(db);
	}

	@Test
	public void getShouldExecuteBackUpProcess() {
		Mockito.when(dataStore.getDB().collectionExists(Mockito.any())).thenReturn(true);
		
		repository.backupCollection();

		verify(dataStore, times(1)).getCollection(ClientGstInfo.class);
	}
	
	@Test
	public void getShouldNotExecuteBackUpProcess() {
		Mockito.when(dataStore.getDB().collectionExists(Mockito.any())).thenReturn(false);
		
		repository.backupCollection();

		verify(dataStore, times(0)).getCollection(ClientGstInfo.class);
	}
}
