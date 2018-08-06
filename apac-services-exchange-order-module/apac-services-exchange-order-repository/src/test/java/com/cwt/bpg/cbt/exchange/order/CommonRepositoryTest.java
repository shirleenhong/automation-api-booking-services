package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

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

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@SuppressWarnings({ "unchecked", "ResultOfMethodCallIgnored" })
public class CommonRepositoryTest {

	@Mock
	private Datastore datastore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private CommonRepository<InsurancePlan, String> repo;

	@Before
	public void setUp() {
        repo = new CommonRepository<>(InsurancePlan.class, "type");
        MockitoAnnotations.initMocks(this);

        when(morphia.getDatastore()).thenReturn(datastore);
    }

    @Test
	public void shouldReturnAllRows() {
		Query<InsurancePlan> query = mock(Query.class);
		when(datastore.createQuery(InsurancePlan.class)).thenReturn(query);
        ArrayList<InsurancePlan> rows = new ArrayList<>();
        when(query.asList()).thenReturn(rows);

		List<InsurancePlan> result = repo.getAll();

		assertThat(result, is(equalTo(rows)));
		verify(morphia, times(1)).getDatastore();
		verify(datastore, times(1)).createQuery(InsurancePlan.class);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldInsertOrUpdate() {
		InsurancePlan insurance = new InsurancePlan();
		insurance.setType("insurance-type");

		Key<InsurancePlan> key = mock(Key.class);
		Query<InsurancePlan> query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(datastore.createQuery(InsurancePlan.class)).thenReturn(query);
		when(query.field("type")).thenReturn(fieldEnd);
		when(fieldEnd.equal(insurance.getType())).thenReturn(query);
		when(datastore.save(insurance)).thenReturn(key);
		WriteResult dkey = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(dkey);

        InsurancePlan result = repo.put(insurance);

        assertThat(result, is(equalTo(insurance)));
        verify(morphia, times(2)).getDatastore();
        verify(datastore, times(1)).save(insurance);
		verify(datastore, times(1)).delete(query);
	}

    @Test
    public void shouldInsertOrUpdateWhenKeyValueIsEmpty() {

		InsurancePlan insurance = new InsurancePlan();

		Key<InsurancePlan> key = mock(Key.class);
		Query<InsurancePlan> query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(datastore.createQuery(InsurancePlan.class)).thenReturn(query);
		when(query.field("type")).thenReturn(fieldEnd);
		when(fieldEnd.equal(insurance.getType())).thenReturn(query);
		when(datastore.save(insurance)).thenReturn(key);
		WriteResult dkey = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(dkey);
		

        InsurancePlan result = repo.put(insurance);

        assertThat(result, is(equalTo(insurance)));
        verify(morphia, times(1)).getDatastore();
        verify(datastore, times(1)).save(insurance);
		verify(datastore, never()).delete(query);
    }

	@Test
	public void shouldUpdateWhenNoRepositoryKey() {

		repo = new CommonRepository<>(InsurancePlan.class,  "");
		MockitoAnnotations.initMocks(this);

		when(morphia.getDatastore()).thenReturn(datastore);

		InsurancePlan insurance = new InsurancePlan();
		insurance.setType("insurance-type");

		Key<InsurancePlan> key = mock(Key.class);
		when(datastore.save(insurance)).thenReturn(key);

		InsurancePlan result = repo.put(insurance);

		assertThat(result, is(equalTo(insurance)));
		verify(morphia, times(1)).getDatastore();
		verify(datastore, times(1)).save(insurance);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldDelete() {
		final String key = "test";

		Query<InsurancePlan> query = mock(Query.class);
		when(datastore.createQuery(InsurancePlan.class)).thenReturn(query);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(query.field("type")).thenReturn(fieldEnd);
		when(fieldEnd.equal(key)).thenReturn(query);

		WriteResult writeResult = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(writeResult);

        String result = repo.remove(key);

        assertThat(result, is(equalTo(key)));
        verify(morphia, times(1)).getDatastore();
        verify(datastore, times(1)).delete(query);
	}
}
