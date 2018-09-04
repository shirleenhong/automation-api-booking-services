package com.cwt.bpg.cbt.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.calculator.model.AirTransactionTest;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@SuppressWarnings({ "unchecked" })
public class CommonRepositoryTest {

	@Mock
	private Datastore datastore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private CommonRepository<AirTransactionTest, String> repo;

	@Before
	public void setUp() {
        repo = new CommonRepository<>(AirTransactionTest.class, "airlineCode");
        MockitoAnnotations.initMocks(this);

        when(morphia.getDatastore()).thenReturn(datastore);
    }

    @Test
	public void shouldReturnAllRows() {
		Query<AirTransactionTest> query = mock(Query.class);
		when(datastore.createQuery(AirTransactionTest.class)).thenReturn(query);
        ArrayList<AirTransactionTest> rows = new ArrayList<>();
        when(query.asList()).thenReturn(rows);

		List<AirTransactionTest> result = repo.getAll();

		assertThat(result, is(equalTo(rows)));
		verify(morphia, times(1)).getDatastore();
		verify(datastore, times(1)).createQuery(AirTransactionTest.class);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldInsertOrUpdate() {
		AirTransactionTest airTransaction = new AirTransactionTest();
		airTransaction.setAirlineCode("airlineCode");

		Key<AirTransactionTest> key = mock(Key.class);
		Query<AirTransactionTest> query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(datastore.createQuery(AirTransactionTest.class)).thenReturn(query);
		when(query.field("airlineCode")).thenReturn(fieldEnd);
		when(fieldEnd.equal(airTransaction.getAirlineCode())).thenReturn(query);
		when(datastore.save(airTransaction)).thenReturn(key);
		WriteResult dkey = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(dkey);

		AirTransactionTest result = repo.put(airTransaction);

        assertThat(result, is(equalTo(airTransaction)));
        verify(morphia, times(2)).getDatastore();
        verify(datastore, times(1)).save(airTransaction);
		verify(datastore, times(1)).delete(query);
	}

    @SuppressWarnings("rawtypes")
	@Test
    public void shouldInsertOrUpdateWhenKeyValueIsEmpty() {

    	AirTransactionTest insurance = new AirTransactionTest();

		Key<AirTransactionTest> key = mock(Key.class);
		Query<AirTransactionTest> query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(datastore.createQuery(AirTransactionTest.class)).thenReturn(query);
		when(query.field("type")).thenReturn(fieldEnd);
		when(fieldEnd.equal(insurance.getAirlineCode())).thenReturn(query);
		when(datastore.save(insurance)).thenReturn(key);
		WriteResult dkey = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(dkey);
		

		AirTransactionTest result = repo.put(insurance);

        assertThat(result, is(equalTo(insurance)));
        verify(morphia, times(1)).getDatastore();
        verify(datastore, times(1)).save(insurance);
		verify(datastore, never()).delete(query);
    }

    @Test
	public void shouldUpdateWhenNoRepositoryKey() {

		repo = new CommonRepository<>(AirTransactionTest.class,  "");
		MockitoAnnotations.initMocks(this);

		when(morphia.getDatastore()).thenReturn(datastore);

		AirTransactionTest airTransaction = new AirTransactionTest();
		airTransaction.setAirlineCode("airlineCode");

		Key<AirTransactionTest> key = mock(Key.class);
		when(datastore.save(airTransaction)).thenReturn(key);

		AirTransactionTest result = repo.put(airTransaction);

		assertThat(result, is(equalTo(airTransaction)));
		verify(morphia, times(1)).getDatastore();
		verify(datastore, times(1)).save(airTransaction);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldDelete() {
		final String key = "test";

		Query<AirTransactionTest> query = mock(Query.class);
		when(datastore.createQuery(AirTransactionTest.class)).thenReturn(query);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(query.field("airlineCode")).thenReturn(fieldEnd);
		when(fieldEnd.equal(key)).thenReturn(query);

		WriteResult writeResult = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(writeResult);

        String result = repo.remove(key);

        assertThat(result, is(equalTo(key)));
        verify(morphia, times(1)).getDatastore();
        verify(datastore, times(1)).delete(query);
	}
}
