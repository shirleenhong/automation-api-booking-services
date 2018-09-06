package com.cwt.bpg.cbt.air.transaction;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class AirTransactionRepositoryTest {

    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private AirTransactionRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void shouldReturnAirTransactionsNullParams(){
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(dataStore.createQuery(AirTransaction.class)).thenReturn(query);
        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);
        when(query.filter(Mockito.anyString(), Mockito.anyObject())).thenReturn(query);

        repository.getAirTransactions(new AirTransactionInput());

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(AirTransaction.class);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void shouldReturnAirTransactions(){
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(dataStore.createQuery(AirTransaction.class)).thenReturn(query);
        when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);
        when(query.filter(Mockito.anyString(), Mockito.anyObject())).thenReturn(query);

        when(query.criteria(anyString())).thenReturn(fieldEnd);
        
        AirTransactionInput input = createAirTransactionInput();
        
        repository.getAirTransactions(input);

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(AirTransaction.class);
    }

	private AirTransactionInput createAirTransactionInput() {
		List<String> bookingClasses = new ArrayList<>();
        bookingClasses.add("A");
        
        AirTransactionInput input = new AirTransactionInput();
        input.setAirlineCode("TG");
        input.setCcVendorCode("V");
        input.setCcType("UATP");
        input.setClientAccountNumber("12345");
        input.setBookingClasses(bookingClasses);
		return input;
	}
}
