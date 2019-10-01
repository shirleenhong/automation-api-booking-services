package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlatTransactionFeeServiceTest
{
    @Mock
    private FlatTransactionFeeRepository flatTransactionFeeRepository;

    @InjectMocks
    private FlatTransactionFeeService flatTransactionFeeService;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canPutFlatTransactionFee()
    {

        FlatTransactionFee input = new FlatTransactionFee();
        FlatTransactionFee result = flatTransactionFeeService.save(input);

        verify(flatTransactionFeeRepository).put(input);
    }


    @Test
    public void canRemoveFlatTransactionFee()
    {
        String clientAccountNumber = "1233333000";
        final String result = flatTransactionFeeService.delete(clientAccountNumber);

        verify(flatTransactionFeeRepository).remove(clientAccountNumber);
    }


    @Test
    public void canGetClientFlatTransactionFee()
    {
        final String clientAccountNumber = "1233333000";
        when(flatTransactionFeeRepository.get(eq(clientAccountNumber))).thenReturn(mock(FlatTransactionFee.class));
        final FlatTransactionFee result = flatTransactionFeeService.getTransactionFee(clientAccountNumber);

        assertNotNull(result);
        verify(flatTransactionFeeRepository).get(clientAccountNumber);
    }
}
