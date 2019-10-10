package com.cwt.bpg.cbt.air.transaction;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;

public class AirTransactionBackupServiceTest
{
    @InjectMocks
    private AirTransactionBackupService service;

    @Mock
    private AirTransactionRepository repository;

    @Mock
    private AirTransactionBackupRepository backupRepository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldArchiveWhenAirTransactionIsNotEmpty()
    {
        Mockito.when(repository.getAll()).thenReturn(Arrays.asList(new AirTransaction()));
        Mockito.when(repository.putAll(Mockito.any())).thenReturn(Arrays.asList(new AirTransaction()));

        service.archive(null, null, false);

        Mockito.verify(backupRepository, Mockito.times(1)).putAll(Mockito.any());
    }
    
    @Test
    public void shouldNotArchiveWhenAirTransactionIsEmpty()
    {
        Mockito.when(repository.getAll()).thenReturn(null);

        service.archive(null, null, false);

        Mockito.verify(backupRepository, Mockito.times(0)).putAll(Mockito.any());
    }
}
