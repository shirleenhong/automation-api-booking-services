package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@RunWith(MockitoJUnitRunner.class)
public class AirMiscInfoRepositoryTest {
	
	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private AirMiscInfoRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getShouldReturnAirMiscInfo() {
		
		List<String> reportingFieldTypeIds = new ArrayList<>();
		reportingFieldTypeIds.add("5");
		
		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(AirMiscInfo.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal(anyString())).thenReturn(query);

		repository.getAirMiscInfos("12345", reportingFieldTypeIds);

		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).createQuery(AirMiscInfo.class);
		verify(query, times(2)).field(anyString());
		verify(fieldEnd, times(1)).equal(anyString());
	}
}
