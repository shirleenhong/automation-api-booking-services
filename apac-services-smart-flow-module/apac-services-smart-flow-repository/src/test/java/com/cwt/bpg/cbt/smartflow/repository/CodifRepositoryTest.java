package com.cwt.bpg.cbt.smartflow.repository;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.cwt.bpg.cbt.smartflow.model.Codif;

public class CodifRepositoryTest {

    @InjectMocks
    private CodifRepository repository;

    @Mock
    private MorphiaComponent morphia;

    @Mock
    private Datastore datastore;

    @Mock
    private Query<Codif> query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(datastore.createQuery(Codif.class)).thenReturn(query);
        when(morphia.getDatastore()).thenReturn(datastore);
    }

    @Test
    public void shouldReturnResultWhenGDsPropIdAndKeyTypeAreNotNull() {
        FieldEnd fieldEnd = mock(FieldEnd.class);
        when(query.field(anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(query);
        when(query.get()).thenReturn(new Codif());

        repository.getCodif("123", "AC");

        verify(query, times(2)).field(anyString());
        verify(morphia, times(1)).getDatastore();
    }
}
