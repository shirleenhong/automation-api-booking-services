package com.cwt.bpg.cbt.air.contract;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.air.contract.model.AirContract;

public class AirContractServiceTest {

    @Mock
    private AirContractRepository repository;

    @InjectMocks
    private AirContractService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getShouldReturnAirContract() {
        AirContract airContract = mock(AirContract.class);
        when(repository.get(anyString(), anyString(), anyString())).thenReturn(airContract);

        AirContract result = service.get("SG", "BR", "3407002");

        assertEquals(airContract, result);
        verify(repository, times(1)).get(anyString(), anyString(), anyString());
    }

    @Test
    public void getByIdShouldReturnAirContract() {
        AirContract airContract = mock(AirContract.class);
        when(repository.get(anyObject())).thenReturn(airContract);

        AirContract result = service.get(new ObjectId().toString());

        assertEquals(airContract, result);
        verify(repository, times(1)).get(anyObject());
    }

    @Test
    public void saveShouldReturnSavedAirContract() {
    	AirContract airContract = mock(AirContract.class);
    	
        when(repository.put(any())).thenReturn(airContract);
        
        AirContract result = service.save(airContract);

        assertEquals(airContract, result);
        verify(repository, times(1)).put(any());
    }

    @Test
    public void deleteShouldReturnWriteResult() {
    	String id = new ObjectId().toString();
        when(repository.remove(any(ObjectId.class))).thenReturn(id);

        String result = service.delete(id);

        assertEquals(id, result);
        verify(repository, times(1)).remove(any(ObjectId.class));
    }
}
