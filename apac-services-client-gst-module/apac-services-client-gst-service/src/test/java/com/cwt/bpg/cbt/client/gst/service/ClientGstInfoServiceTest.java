package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.exceptions.FileUploadException;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

@RunWith(MockitoJUnitRunner.class)
public class ClientGstInfoServiceTest
{

    @Mock
    private ClientGstInfoExcelReaderService clientGstInfoExcelReaderService;

    @Mock
    private ClientGstInfoGroupService clientGstInfoBackupService;

    @SuppressWarnings("rawtypes")
    @Mock
    private Map<String, ClientGstInfoReaderService> map;

    @Mock
    private ClientGstInfoGroupService groupService;

    @Mock
    private ClientGstInfoRepository clientGstInfoRepository;

    @Mock
    private ClientGstInfoFileWriterService clientGstInfoFileWriterService;

    @InjectMocks
    private ClientGstInfoService service;

    private InputStream inputStream;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        ClientGstInfo clientGstInfo = new ClientGstInfo();
        when(clientGstInfoExcelReaderService.readFile(any(), anyBoolean())).thenReturn(Arrays.asList(clientGstInfo));

        when(map.get("xlsx")).thenReturn(clientGstInfoExcelReaderService);
    }

    @Test
    public void shouldReturnAllGstInfoByGstIn()
    {
        when(groupService.getClientGstInfoActiveCollection()).thenReturn(new CollectionGroup());
        List<ClientGstInfo> response = service.getAllClientGstInfo();

        assertNotNull(response);
    }

    @Test
    public void shouldHandleNullGstInfo()
    {
        final String givenGstIn = "123456";

        when(groupService.getClientGstInfoActiveCollection()).thenReturn(new CollectionGroup());
        final ClientGstInfo response = service.getClientGstInfo(givenGstIn);

        assertNull(response);
    }

    @Test
    public void shouldSaveGstInfo()
    {
        final ClientGstInfo givenInfo = new ClientGstInfo();
        givenInfo.setGstin("12345");

        when(clientGstInfoRepository.put(any())).thenReturn(givenInfo);
        when(groupService.getClientGstInfoActiveCollection()).thenReturn(new CollectionGroup());
        final ClientGstInfo response = service.save(givenInfo);

        assertEquals(givenInfo.getGstin(), response.getGstin());
    }

    @Test
    public void shouldRemoveGstInfo()
    {
        final String givenGstIn = "123456";

        when(clientGstInfoRepository.remove(anyString())).thenReturn(givenGstIn);
        final String response = service.remove(givenGstIn);

        assertEquals(givenGstIn, response);
    }

    @Test
    public void shouldSaveFromExcelFile() throws Exception
    {
        when(groupService.createClientGstInfoGroup()).thenReturn(new CollectionGroup());
        service.saveFromFile(inputStream, "xlsx", false);

        // verify if backup is performed
        verify(groupService, times(0)).rollbackClientGstInfoGroup((any()));
        verify(clientGstInfoRepository, times(1)).putAll(anyCollection());
    }

    @Test
    public void shouldNotBackupWhenClientGstInfoDoesntExist() throws Exception
    {
        when(groupService.createClientGstInfoGroup()).thenReturn(new CollectionGroup());

        service.saveFromFile(inputStream, "xlsx", false);

        // verify if backup is not performed
        verify(groupService, times(0)).rollbackClientGstInfoGroup((any()));

        verify(clientGstInfoRepository, times(1)).putAll(anyCollection());
    }

    @Test
    public void shouldNotSaveClientGstInfoIfNoneIsExtractedFromExcel() throws Exception
    {

        when(clientGstInfoExcelReaderService.readFile(any(), anyBoolean())).thenReturn(Collections.emptyList());

        service.saveFromFile(inputStream, "xlsx", false);

        // verify if clientGstInfo collection is not replaced with new collection
        verify(clientGstInfoRepository, times(0)).dropCollection();
        verify(clientGstInfoRepository, times(1)).putAll(Collections.emptyList());
    }

    @Test
    public void shouldWriteFile() throws Exception
    {
        when(clientGstInfoRepository.getAll()).thenReturn(Arrays.asList(new ClientGstInfo()));
        when(clientGstInfoFileWriterService.writeToFile(anyList())).thenReturn(new WriteClientGstInfoFileResponse());

        WriteClientGstInfoFileResponse result = service.writeFile();
        assertNotNull(result);

        verify(clientGstInfoFileWriterService, times(1)).writeToFile(anyList());
    }

    @Test
    public void shouldNotWriteFileIfClientGstInfoListIsEmpty() throws Exception
    {
        when(clientGstInfoRepository.getAll()).thenReturn(Collections.emptyList());

        WriteClientGstInfoFileResponse result = service.writeFile();
        assertNull(result);
        verify(clientGstInfoFileWriterService, times(0)).writeToFile(anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() throws Exception
    {
        when(map.get(any())).thenReturn(null);
        service.saveFromFile(inputStream, "xlsx", false);
        verify(clientGstInfoFileWriterService, times(0)).writeToFile(anyList());
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = FileUploadException.class)
    public void shouldThrowFileUploadException() throws Exception
    {
        ClientGstInfoReaderService reader = mock(ClientGstInfoReaderService.class);
        when(map.get(any())).thenReturn(reader);
        when(reader.readFile(any(), anyBoolean())).thenThrow(FileUploadException.class);
        when(groupService.getClientGstInfoActiveCollection()).thenReturn(new CollectionGroup());
        service.saveFromFile(inputStream, "xlsx", false);
        verify(groupService, times(1)).rollbackClientGstInfoGroup((any()));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = Exception.class)
    public void shouldThrowException() throws Exception
    {
        ClientGstInfoReaderService reader = mock(ClientGstInfoReaderService.class);
        when(map.get(any())).thenReturn(reader);
        when(clientGstInfoRepository.putAll(any())).thenThrow(NullPointerException.class);
        service.saveFromFile(inputStream, "xlsx", false);
        verify(groupService, times(1)).rollbackClientGstInfoGroup((any()));
    }
}
