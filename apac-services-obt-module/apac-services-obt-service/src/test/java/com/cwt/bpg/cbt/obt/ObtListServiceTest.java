package com.cwt.bpg.cbt.obt;

import com.cwt.bpg.cbt.obt.model.ObtList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ObtListServiceTest {

    @Mock
    private ObtListRepository repository;

    @InjectMocks
    private ObtListService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getShouldReturnObtList() {
		ObtList obtList = mock(ObtList.class);
        when(repository.get(anyString())).thenReturn(obtList);

        ObtList result = service.get("IN");

        assertEquals(obtList, result);
        verify(repository, times(1)).get(anyString());
    }

    @Test
    public void saveShouldReturnSavedObtList() {
        ObtList obtList = new ObtList();

        when(repository.put(any(ObtList.class))).thenReturn(obtList);

        ObtList result = service.save(new ObtList());

        assertEquals(obtList, result);
        verify(repository, times(1)).put(any(ObtList.class));
    }

    @Test
    public void deleteShouldReturnWriteResult() {
    	String countryCode = "IN";
        when(repository.remove(anyString())).thenReturn(countryCode);

        String result = service.delete(countryCode);

        assertEquals(countryCode, result);
        verify(repository, times(1)).remove(anyString());
    }
}
