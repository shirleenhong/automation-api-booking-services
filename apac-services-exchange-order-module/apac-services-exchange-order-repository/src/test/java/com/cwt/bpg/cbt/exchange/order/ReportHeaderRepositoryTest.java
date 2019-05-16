package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@RunWith(MockitoJUnitRunner.class)
public class ReportHeaderRepositoryTest {
    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private ReportHeaderRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void getReportHeaderShouldReturnReportHeader() {
        Query query = Mockito.mock(Query.class);
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        Mockito.when(dataStore.createQuery(ReportHeader.class)).thenReturn(query);
        Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
        Mockito.when(fieldEnd.equalIgnoreCase(anyString())).thenReturn(query);

        repository.get("CWT SG");

        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).createQuery(ReportHeader.class);
    }

    @Test
    public void shouldSave() {
        ReportHeader rh = new ReportHeader();
        rh.setCountryCode("SG");
        rh.setAddress("Dummy Address");

        Key<ReportHeader> key = mock(Key.class);
        when(dataStore.save(rh)).thenReturn(key);

        ReportHeader result = repository.put(rh);

        assertThat(result, is(equalTo(rh)));
        verify(morphia, times(1)).getDatastore();
        verify(dataStore, times(1)).save(rh);
    }

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldDelete() {
		final String key = "SG";

		Query<ReportHeader> query = mock(Query.class);
		when(dataStore.createQuery(ReportHeader.class)).thenReturn(query);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(query.field("countryCode")).thenReturn(fieldEnd);
		when(fieldEnd.equalIgnoreCase(key)).thenReturn(query);

		WriteResult writeResult = new WriteResult(1, true, null);
		when(dataStore.delete(query)).thenReturn(writeResult);

		String result = repository.remove(key);

		assertThat(result, is(equalTo(key)));
		verify(morphia, times(1)).getDatastore();
		verify(dataStore, times(1)).delete(query);
	}
}
