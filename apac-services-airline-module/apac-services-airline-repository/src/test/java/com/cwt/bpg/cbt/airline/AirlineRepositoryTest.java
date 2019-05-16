package com.cwt.bpg.cbt.airline;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.airline.model.Airline;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

public class AirlineRepositoryTest {

    @InjectMocks
    private AirlineRepository repository;

    @Mock
    private MorphiaComponent morphia;

    @Mock
    private Datastore datastore;

    @Mock
    private Query<Airline> query;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(datastore.createQuery(Airline.class)).thenReturn(query);
        when(morphia.getDatastore()).thenReturn(datastore);
    }

    @Test
    public void shouldReturnResultWhenAirlineCodeIsNotNull() {
        ArrayList<Airline> expected = new ArrayList<>();
        when(query.asList()).thenReturn(expected);
        when(query.field(anyString())).thenReturn(mock(FieldEnd.class));

        List<Airline> actual = repository.getAirlines("AA");

        InOrder inOrder = inOrder(morphia, datastore, query);
        inOrder.verify(morphia).getDatastore();
        inOrder.verify(datastore).createQuery(Airline.class);
        inOrder.verify(query).field(anyString());
        inOrder.verify(query).asList();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnResultWhenAirlineCodeIsNull() {
        ArrayList<Airline> expected = new ArrayList<>();
        when(query.asList()).thenReturn(expected);
        when(query.field(anyString())).thenReturn(mock(FieldEnd.class));

        List<Airline> actual = repository.getAirlines(null);

        InOrder inOrder = inOrder(morphia, datastore, query);
        inOrder.verify(morphia).getDatastore();
        inOrder.verify(datastore).createQuery(Airline.class);
        inOrder.verify(query).asList();
        inOrder.verifyNoMoreInteractions();

        assertEquals(expected, actual);
    }
}
