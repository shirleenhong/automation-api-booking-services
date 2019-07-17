package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.*;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoBackupRepository;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.exceptions.FileUploadException;
import com.mongodb.CommandResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientGstInfoServiceTest {

    @Mock
    private ClientGstInfoExcelReaderService clientGstInfoExcelReaderService;

    @Mock
    private Map<String, ClientGstInfoReaderService> map;

    @Mock
    private ClientGstInfoBackupRepository clientGstInfoBackupRepository;

    @Mock
    private ClientGstInfoRepository clientGstInfoRepository;

    @Mock
    private ClientGstInfoFileWriterService clientGstInfoFileWriterService;

    @InjectMocks
    private ClientGstInfoService service;

    private InputStream inputStream;

    @Before
    public void setup() throws FileUploadException {
        MockitoAnnotations.initMocks(this);
        ClientGstInfo clientGstInfo = new ClientGstInfo();
        when(clientGstInfoExcelReaderService.readFile(any())).thenReturn(Arrays.asList(clientGstInfo));

        //get collection count/size
        CommandResult mockCommandResult = Mockito.mock(CommandResult.class);
        when(mockCommandResult.get("count")).thenReturn(5);
        when(clientGstInfoRepository.getStats()).thenReturn(mockCommandResult);
        when(clientGstInfoRepository.getAll(any())).thenReturn(Arrays.asList(clientGstInfo));
        inputStream = Mockito.mock(InputStream.class);
        when(map.get("xlsx")).thenReturn(clientGstInfoExcelReaderService);
    }

    @Test
    public void shouldReturnAllGstInfoByGstIn() {
        when(clientGstInfoRepository.getAll())
                .thenReturn(Arrays.asList(new ClientGstInfo(), new ClientGstInfo()));

        List<ClientGstInfo> response = service.getAllClientGstInfo();

        assertNotNull(response);
    }

    @Test
    public void shouldHandleNullGstInfo() {
        final String givenGstIn = "123456";

        when(clientGstInfoRepository.get(anyString())).thenReturn(null);

        final ClientGstInfo response = service
                .getClientGstInfo(givenGstIn);

        assertNull(response);
    }

    @Test
    public void shouldSaveGstInfo() {
        final ClientGstInfo givenInfo = new ClientGstInfo();
        givenInfo.setGstin("12345");

        when(clientGstInfoRepository.put(anyObject())).thenReturn(givenInfo);
        final ClientGstInfo response = service.save(givenInfo);

        assertEquals(givenInfo.getGstin(), response.getGstin());
    }

    @Test
    public void shouldRemoveGstInfo() {
        final String givenGstIn = "123456";

        when(clientGstInfoRepository.remove(anyString())).thenReturn(givenGstIn);
        final String response = service.remove(givenGstIn);

        assertEquals(givenGstIn, response);
    }

    @Test
    public void shouldSaveFromExcelFile() {
        service.saveFromFile(inputStream, "xlsx");

        //verify if backup is performed
        ArgumentCaptor<List> backupsCaptor = ArgumentCaptor.forClass(List.class);
        verify(clientGstInfoBackupRepository, times(1)).putAll(backupsCaptor.capture());

        List<ClientGstInfoBackup> backups = (List<ClientGstInfoBackup>)backupsCaptor.getValue();
        ClientGstInfoBackup backup = backups.get(0);

        assertNotNull(backup.getDateCreated());
        assertNotNull(backup.getClientGstInfo());

        //verify if clientGstInfo collection is replaced with new collection
        verify(clientGstInfoRepository, times(1)).dropCollection();
        verify(clientGstInfoRepository, times(1)).putAll(anyCollection());
    }

    @Test
    public void shouldNotBackupWhenClientGstInfoDoesntExist() throws Exception{
        CommandResult commandResult = Mockito.mock(CommandResult.class);
        when(commandResult.get("count")).thenReturn(null);
        when(clientGstInfoRepository.getStats()).thenReturn(commandResult);

        service.saveFromFile(inputStream, "xlsx");

        //verify if backup is not performed
        verify(clientGstInfoBackupRepository, times(0)).dropCollection();

        //verify if clientGstInfo collection is replaced with new collection
        verify(clientGstInfoRepository, times(1)).dropCollection();
        verify(clientGstInfoRepository, times(1)).putAll(anyCollection());
    }

    @Test
    public void shouldNotSaveClientGstInfoIfNoneIsExtractedFromExcel() throws Exception{

        when(clientGstInfoExcelReaderService.readFile(any())).thenReturn(Collections.emptyList());

        service.saveFromFile(inputStream, "xlsx");

        //verify if clientGstInfo collection is not replaced with new collection
        verify(clientGstInfoRepository, times(0)).dropCollection();
        verify(clientGstInfoRepository, times(0)).putAll(anyCollection());
    }

    @Test
    public void shouldWriteFile() throws Exception {
        when(clientGstInfoRepository.getAll())
                .thenReturn(Arrays.asList(new ClientGstInfo()));
        when(clientGstInfoFileWriterService.writeToFile(anyList()))
                .thenReturn(new WriteClientGstInfoFileResponse());

        WriteClientGstInfoFileResponse result = service.writeFile();
        assertNotNull(result);

        verify(clientGstInfoFileWriterService, times(1)).writeToFile(anyList());
    }

    @Test
    public void shouldNotWriteFileIfClientGstInfoListIsEmpty() throws Exception {
        when(clientGstInfoRepository.getAll())
                .thenReturn(Collections.emptyList());

        WriteClientGstInfoFileResponse result = service.writeFile();
        assertNull(result);
        verify(clientGstInfoFileWriterService, times(0)).writeToFile(anyList());
    }
}
