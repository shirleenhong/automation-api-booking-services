package com.cwt.bpg.cbt.air.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.mongodb.CommandResult;

@RunWith(MockitoJUnitRunner.class)
public class AirTransactionBackupServiceTest {
	
	@InjectMocks
	private AirTransactionBackupService service;

	@Mock
	private AirTransactionRepository repository;

	@Mock
	private AirTransactionBackupRepository backupRepository;

	@Mock
	private List<AirTransaction> sourceList;

	@Test
	public void shouldNotBackup() {
		CommandResult stats = mock(CommandResult.class);
		when(repository.getStats()).thenReturn(stats);
		when(stats.get(anyString())).thenReturn(0);
		service.archive(Collections.EMPTY_LIST, "");
		verify(repository, times(1)).getStats();
		verify(repository, never()).dropCollection();
		verify(backupRepository, never()).putAll(any());
	}

	@Test
	public void shouldBackup() {
		CommandResult stats = mock(CommandResult.class);
		when(repository.getStats()).thenReturn(stats);
		List<AirTransaction> list = new ArrayList<AirTransaction>();
		AirTransaction airTransaction = new AirTransaction();
		list.add(airTransaction);
		when(repository.getAll(any())).thenReturn(list);
		when(stats.get(anyString())).thenReturn(10);

		service.archive(new ArrayList<AirTransaction>(), "", true);
		verify(repository, times(1)).getStats();
		verify(repository, times(1)).dropCollection();
		verify(backupRepository, times(1)).dropCollection();
		verify(backupRepository, times(1)).putAll(any());
	}

	@Test
	public void shouldRollback() {
		List<AirTransaction> list = new ArrayList<AirTransaction>();
		AirTransaction airTransaction = new AirTransaction();
		list.add(airTransaction);

		service.rollback(list, "");
		verify(backupRepository, times(1)).removeBatchBackup(any());
		verify(repository, times(1)).putAll(any());
	}
}
