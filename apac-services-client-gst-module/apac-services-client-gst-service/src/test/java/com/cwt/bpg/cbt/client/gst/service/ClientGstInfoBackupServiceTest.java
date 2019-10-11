package com.cwt.bpg.cbt.client.gst.service;

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

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoBackupRepository;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.mongodb.CommandResult;

@RunWith(MockitoJUnitRunner.class)
public class ClientGstInfoBackupServiceTest {

	@InjectMocks
	private ClientGstInfoBackupService clientGstInfoBackupService;
	
	@Mock
	private ClientGstInfoRepository repository;

	@Mock
	private ClientGstInfoBackupRepository backupRepository;

	@Test
	public void shouldNotBackup() {
		CommandResult stats = mock(CommandResult.class);
		when(repository.getStats()).thenReturn(stats);
		when(stats.get(anyString())).thenReturn(0);
		clientGstInfoBackupService.archive(Collections.EMPTY_LIST, "");
		verify(repository, times(1)).getStats();
		verify(repository, never()).dropCollection();
		verify(backupRepository, never()).putAll(any());
	}
	
	@Test
	public void shouldBackup() {
		CommandResult stats = mock(CommandResult.class);
		when(repository.getStats()).thenReturn(stats);
		List<ClientGstInfo> list = new ArrayList<ClientGstInfo>();
		ClientGstInfo clientGstInfo = new ClientGstInfo();
		list.add(clientGstInfo);
		when(repository.getAll(any())).thenReturn(list);
		when(stats.get(anyString())).thenReturn(10);
		
		clientGstInfoBackupService.archive(new ArrayList<ClientGstInfo>() , "", true);
		verify(repository, times(1)).getStats();
		verify(repository, times(1)).dropCollection();
		verify(backupRepository, times(1)).dropCollection();
		verify(backupRepository, times(1)).putAll(any());
	}

	@Test
	public void shouldRollback() {
		List<ClientGstInfo> list = new ArrayList<ClientGstInfo>();
		ClientGstInfo clientGstInfo = new ClientGstInfo();
		list.add(clientGstInfo);

		clientGstInfoBackupService.rollback(list, "");
		verify(backupRepository, times(1)).removeBatchBackup(any());
		verify(repository, times(1)).putAll(any());
	}
}
