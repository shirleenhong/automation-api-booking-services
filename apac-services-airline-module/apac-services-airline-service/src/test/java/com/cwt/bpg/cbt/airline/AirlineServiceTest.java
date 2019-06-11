package com.cwt.bpg.cbt.airline;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.airline.model.Airline;

public class AirlineServiceTest {

    @InjectMocks
    private AirlineService service;

    @Mock
    private AirlineRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnExistingAirline() {
        List<Airline> expected = Collections.singletonList(new Airline());
        when(repository.getAirlines(anyString())).thenReturn(expected);

        Airline actual = service.getAirline("AA");

        verify(repository).getAirlines("AA");
        assertNotNull(actual);
    }

    @Test
    public void shouldReturnNullWhenAirlineDoesNotExist() {
        List<Airline> expected = Collections.emptyList();
        when(repository.getAirlines(anyString())).thenReturn(expected);

        Airline actual = service.getAirline("AA");

        verify(repository).getAirlines("AA");
        assertNull(actual);
    }

    @Test
    public void shouldGetAirlines() {
        List<Airline> expected = new ArrayList<>();
        when(repository.getAirlines(null)).thenReturn(expected);

        List<Airline> actual = service.getAirlines();

        verify(repository).getAirlines(null);
        assertEquals(actual, expected);
    }

    @Test
    public void shouldSave() {
        Airline expected = mock(Airline.class);
        when(repository.put(any(Airline.class))).thenReturn(expected);

        Airline savedAirline = service.save(expected);

        verify(repository).put(expected);
        assertThat(savedAirline, is(equalTo(expected)));
    }

    @Test
    public void shouldDelete() {
        String expected = "AA";
        when(repository.remove(anyString())).thenReturn(expected);

        String actual = service.delete(expected);

        verify(repository).remove(expected);
        assertThat(actual, is(equalTo(expected)));
    }
}
