package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

public class FlatTransactionFeeRepositoryTest
{

    @Mock
    private Datastore dataStore;

    @Mock
    private MorphiaComponent morphia;

    @InjectMocks
    private FlatTransactionFeeRepository flatTransactionFeeRepository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(morphia.getDatastore()).thenReturn(dataStore);
    }

    @Test
    public void canGetFlatTransactionFee()
    {

        Query<FlatTransactionFee> query = mock(Query.class);
        FieldEnd fieldEnd = mock(FieldEnd.class);

        when(dataStore.createQuery(FlatTransactionFee.class)).thenReturn(query);
        when(query.field(anyString())).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);
        when(query.get()).thenReturn(createFlatTransactionFee());

        final String clientAccountNumber = "1234567890";
        FlatTransactionFee result = flatTransactionFeeRepository.get(clientAccountNumber);

        verify(morphia).getDatastore();
        verify(dataStore).createQuery(FlatTransactionFee.class);

        assertEquals(BigDecimal.ONE, result.getDomOfflineAmount());
        assertEquals(BigDecimal.TEN, result.getIntOfflineAmount());
        assertEquals(BigDecimal.valueOf(100), result.getLccOfflineAmount());

        assertNotNull(result);
    }

    private static FlatTransactionFee createFlatTransactionFee()
    {
        final FlatTransactionFee fee = new FlatTransactionFee();
        fee.setClientAccountNumber("1234567890");
        fee.setDomOfflineAmount(BigDecimal.ONE);
        fee.setIntOfflineAmount(BigDecimal.TEN);
        fee.setLccOfflineAmount(BigDecimal.valueOf(100));
        return fee;
    }
}
