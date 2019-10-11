package com.cwt.bpg.cbt.air.transaction;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.mongodb.CommandResult;

public class AirTransactionBackupServiceTest
{
    @InjectMocks
    private AirTransactionBackupService service;

    @Mock
    private AirTransactionRepository repository;

    @Mock
    private AirTransactionBackupRepository backupRepository;

    @Mock
	private List<AirTransaction> sourceList;

    @Mock
	private CommandResult commandResult;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
		when(repository.getStats()).thenReturn(commandResult);
    }

    @Test
    public void shouldArchiveWhenAirTransactionIsNotEmpty()
    {
    	when(commandResult.get("count")).thenReturn(2);
        when(repository.getAll()).thenReturn(Arrays.asList(new AirTransaction()));
        when(repository.putAll(any())).thenReturn(Arrays.asList(new AirTransaction()));

        service.archive(sourceList, null, false);

        verify(backupRepository, times(1)).putAll(any());
    }
    
    @Test
    public void shouldNotArchiveWhenAirTransactionIsEmpty()
    {
        Mockito.when(repository.getAll()).thenReturn(null);

        service.archive(sourceList, null, false);

        Mockito.verify(backupRepository, Mockito.times(0)).putAll(Mockito.any());
    }
}
