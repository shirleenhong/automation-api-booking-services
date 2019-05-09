package com.cwt.bpg.cbt.smartflow.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.cwt.bpg.cbt.smartflow.model.Codif;
import com.cwt.bpg.cbt.smartflow.repository.CodifRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CodifServiceTest {
	
	@InjectMocks
	private CodifService service;
	
	@Mock
	private CodifRepository repository;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void shouldReturnExistingCodif() {
        Codif expected = new Codif();
        when(repository.getCodif(anyString(), anyString())).thenReturn(expected);

        Codif actual = service.getCodif("ABC123", "sg");

        verify(repository).getCodif("ABC123", "sg");
        assertNotNull(actual);
    }

    @Test
    public void shouldReturnNullWhenCodifDoesNotExist() {
        when(repository.getCodif(anyString(), anyString())).thenReturn(null);

        Codif actual = service.getCodif("ABC123", "sg");

        verify(repository).getCodif("ABC123", "sg");
        assertNull(actual);
    }

    @Test
    public void shouldGetCodifs() {
        List<Codif> expected = new ArrayList<>();
        when(repository.getAll()).thenReturn(expected);

        List<Codif> actual = service.getCodifs();

        verify(repository).getAll();
        assertEquals(actual, expected);
    }

    @Test
    public void shouldSave() {
        Codif expected = mock(Codif.class);
        when(repository.put(any(Codif.class))).thenReturn(expected);

        Codif savedCodif = service.save(expected);

        verify(repository).put(expected);
        assertThat(savedCodif, is(equalTo(expected)));
    }

    @Test
    public void shouldDelete() {
        String expected = "5b2870d6284b8d1ac84300ad";
        Codif codif = new Codif();
        ObjectId objectId = new ObjectId(expected);
        when(repository.get(objectId)).thenReturn(codif);
        when(repository.removeCodif(anyString(), anyString())).thenReturn(expected);

        String actual = service.remove(expected);

        verify(repository, times(1)).removeCodif(anyString(), anyString());
        assertThat(actual, is(equalTo(expected)));
    }
}
