package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlatTransactionFeeServiceTest
{
    @Mock
    private FlatTransactionFeeRepository repository;

    @InjectMocks
    FlatTransactionFeeService service;

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void shoulGetTransactionFee()
    {
        String cn = "10000000";
        when(repository.get(eq(cn))).thenReturn(mock(FlatTransactionFee.class));
        FlatTransactionFee result = service.getTransactionFee(cn);
        assertNotNull(result);
        verify(repository, times(1)).get(cn);
    }
}
