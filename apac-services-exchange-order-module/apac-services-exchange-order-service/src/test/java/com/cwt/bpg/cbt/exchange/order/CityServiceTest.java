package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.City;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class CityServiceTest {

    @Mock
    private CityRepository repository;

    @InjectMocks
    private CityService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllShouldGetAllCities() {
        List<City> cities = mock(List.class);
        when(service.getAll()).thenReturn(cities);

        List<City> result = service.getAll();

        assertNotNull(result);
        verify(repository, times(1)).getAll();
    }

    @Test
    public void getCitiesShouldReturnCitiesWithMatchingCode() {
        List<City> cities = mock(List.class);
        when(service.getCities("MNL")).thenReturn(cities);

        List<City> result = service.getCities("MNL");

        assertNotNull(result);
        verify(repository, times(1)).getCities("MNL");
    }

    @Test
    public void saveShouldReturnSavedCity() {
        City city = mock(City.class);
        when(service.save(city)).thenReturn(city);

        City result = service.save(city);

        assertNotNull(result);
        verify(repository, times(1)).put(any(City.class));
    }

    @Test
    public void deleteShouldReturnWriteResult() {
        City city = mock(City.class);
        when(service.delete(city)).thenReturn("result");

        String result = service.delete(city);

        assertNotNull(result);
        verify(repository, times(1)).remove(any(City.class));
    }
}
