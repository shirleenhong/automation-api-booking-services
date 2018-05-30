package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.Airport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class AirportServiceTest {

    @Mock
    private AirportRepository repository;

    @InjectMocks
    private AirportService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllShouldGetAllAirports() {
        List<Airport> airports = mock(List.class);
        when(service.getAll()).thenReturn(airports);

        List<Airport> result = service.getAll();

        assertNotNull(result);
        verify(repository, times(1)).getAll();
    }

    @Test
    public void getAirportsShouldReturnAirport() {
        Airport airport = mock(Airport.class);
        when(service.getAirport("MNL")).thenReturn(airport);

        Airport result = service.getAirport("MNL");

        assertNotNull(result);
        verify(repository, times(1)).get("MNL");
    }

    @Test
    public void saveShouldReturnSavedAirport() {
        Airport airport = mock(Airport.class);
        when(service.save(airport)).thenReturn(airport);

        Airport result = service.save(airport);

        assertNotNull(result);
        verify(repository, times(1)).put(any(Airport.class));
    }

    @Test
    public void deleteShouldReturnWriteResult() {

        when(service.delete(anyString())).thenReturn("result");

        String result = service.delete("MNL");

        assertNotNull(result);
        verify(repository, times(1)).remove(anyString());
    }
}
