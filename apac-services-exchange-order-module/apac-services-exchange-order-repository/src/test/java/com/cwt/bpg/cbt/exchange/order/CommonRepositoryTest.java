package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf;
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

import com.cwt.bpg.cbt.exchange.order.model.Insurance;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@SuppressWarnings({ "unchecked", "ResultOfMethodCallIgnored" })
public class CommonRepositoryTest {

	@Mock
	private Datastore datastore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private CommonRepository<Insurance> repo;

	@Before
	public void setUp() {
        repo = new CommonRepository<>(Insurance.class, "type");
        MockitoAnnotations.initMocks(this);

        when(morphia.getDatastore()).thenReturn(datastore);
    }

    @Test
	public void shouldReturnAllRows() {
		Query<Insurance> query = mock(Query.class);
		when(datastore.createQuery(Insurance.class)).thenReturn(query);
        ArrayList<Insurance> rows = new ArrayList<>();
        when(query.asList()).thenReturn(rows);

		List<Insurance> result = repo.getAll();

		assertThat(result, is(equalTo(rows)));
		verify(morphia, times(1)).getDatastore();
		verify(datastore, times(1)).createQuery(Insurance.class);
	}

	@Test
	public void shouldInsertOrUpdate() {
		Insurance insurance = new Insurance();
		insurance.setType("insurance-type");

		Key<Insurance> key = mock(Key.class);
		Query<Insurance> query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(datastore.createQuery(Insurance.class)).thenReturn(query);
		when(query.field("type")).thenReturn(fieldEnd);
		when(fieldEnd.equal(insurance.getType())).thenReturn(query);
		when(datastore.save(insurance)).thenReturn(key);
		WriteResult dkey = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(dkey);

        Insurance result = repo.put(insurance);

        assertThat(result, is(equalTo(insurance)));
        verify(morphia, times(2)).getDatastore();
        verify(datastore, times(1)).save(insurance);
		verify(datastore, times(1)).delete(query);
	}

    @Test
    public void shouldInsertOrUpdateWhenKeyValueIsEmpty() {
        repo = new CommonRepository<>(Insurance.class, "notKeyColumn");
        MockitoAnnotations.initMocks(this);

        Insurance insurance = new Insurance();
        insurance.setType("insurance-type");

        Insurance result = repo.put(insurance);

        assertThat(result, is(equalTo(insurance)));
        verifyZeroInteractions(morphia);
        verifyZeroInteractions(datastore);
    }

	@Test
	public void shouldDelete() {
		final String toDelete = "test";

		Query<Insurance> query = mock(Query.class);
		when(datastore.createQuery(Insurance.class)).thenReturn(query);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(query.field("type")).thenReturn(fieldEnd);
		when(fieldEnd.equal(toDelete)).thenReturn(query);

		WriteResult key = new WriteResult(1, true, null);
		when(datastore.delete(query)).thenReturn(key);

        String result = repo.remove(toDelete);

        assertThat(result, is(equalTo(key.toString())));
        verify(morphia, times(1)).getDatastore();
        verify(datastore, times(1)).delete(query);
	}
}
