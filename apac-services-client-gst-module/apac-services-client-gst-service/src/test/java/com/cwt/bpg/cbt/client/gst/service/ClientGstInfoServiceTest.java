package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.*;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoBackupRepository;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.client.gst.repository.GstAirlineRepository;
import com.mongodb.CommandResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientGstInfoServiceTest {

    @Mock
    private ClientGstInfoExcelReaderService clientGstInfoExcelReaderService;

    @Mock
    private ClientGstInfoBackupRepository clientGstInfoBackupRepository;

    @Mock
    private ClientGstInfoRepository clientGstInfoRepository;

    @Mock
    private GstAirlineRepository gstAirlineRepository;

    @InjectMocks
    private ClientGstInfoService service;

    private InputStream inputStream;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientGstInfo clientGstInfo = new ClientGstInfo();
        GstAirline gstAirline = new GstAirline();

        GstLookup mockGstLookup = new GstLookup();
        mockGstLookup.setGstAirlines(Arrays.asList(gstAirline));
        mockGstLookup.setClientGstInfo(Arrays.asList(clientGstInfo));

        when(clientGstInfoExcelReaderService.readExcelFile(any(), any())).thenReturn(mockGstLookup);

        //get collection count/size
        CommandResult mockCommandResult = Mockito.mock(CommandResult.class);
        when(mockCommandResult.get("count")).thenReturn(5);
        when(clientGstInfoRepository.getStats()).thenReturn(mockCommandResult);
        when(clientGstInfoRepository.getAll(any())).thenReturn(Arrays.asList(clientGstInfo));
        inputStream = Mockito.mock(InputStream.class);
    }

    @Test
    public void shouldReturnAllGstInfoByGstIn() {
        when(clientGstInfoRepository.getAll())
                .thenReturn(Arrays.asList(new ClientGstInfo(), new ClientGstInfo()));

        List<ClientGstInfo> response = service.getAllClientGstInfo();

        assertNotNull(response);
    }

    @Test
    public void shouldReturnGstInfoByGstInWithAirlines() {
        final String givenGstIn = "123456";

        final GstAirline xsAirline = new GstAirline();
        xsAirline.setCode("XS");

        final GstAirline cxAirline = new GstAirline();
        cxAirline.setCode("CX");

        when(gstAirlineRepository.getAll()).thenReturn(Arrays.asList(xsAirline, cxAirline));
        when(clientGstInfoRepository.get(anyString())).thenReturn(new ClientGstInfo());

        final ClientGstInfoResponse response = service
                .getClientGstInfo(givenGstIn, Arrays.asList("XS", "CX", "LH"));

        assertNotNull(response.getAirlineCodes());
        assertNotNull(response.getClientGstInfo());

    }

    @Test
    public void shouldHandleNullGstInfo() {
        final String givenGstIn = "123456";

        final GstAirline xsAirline = new GstAirline();
        xsAirline.setCode("XS");

        final GstAirline cxAirline = new GstAirline();
        cxAirline.setCode("CX");

        when(gstAirlineRepository.getAll()).thenReturn(Arrays.asList(xsAirline, cxAirline));
        when(clientGstInfoRepository.get(anyString())).thenReturn(null);

        final ClientGstInfoResponse response = service
                .getClientGstInfo(givenGstIn, Arrays.asList("XS", "CX", "LH"));

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
        service.saveFromExcelFile(inputStream, true);

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

        //verify if gstAirlines collection is replaced with new collection
        verify(gstAirlineRepository, times(1)).dropCollection();
        verify(gstAirlineRepository, times(1)).putAll(anyCollection());
    }

    @Test
    public void shouldNotBackupWhenClientGstInfoDoesntExist() {
        CommandResult commandResult = Mockito.mock(CommandResult.class);
        when(commandResult.get("count")).thenReturn(null);
        when(clientGstInfoRepository.getStats()).thenReturn(commandResult);

        service.saveFromExcelFile(inputStream, true);

        //verify if backup is not performed
        verify(clientGstInfoBackupRepository, times(0)).dropCollection();

        //verify if clientGstInfo collection is replaced with new collection
        verify(clientGstInfoRepository, times(1)).dropCollection();
        verify(clientGstInfoRepository, times(1)).putAll(anyCollection());

        //verify if gstAirlines collection is replaced with new collection
        verify(gstAirlineRepository, times(1)).dropCollection();
        verify(gstAirlineRepository, times(1)).putAll(anyCollection());
    }

    @Test
    public void shouldNotSaveGstAirlinesIfFlagIsFalse() {
        service.saveFromExcelFile(inputStream, false);

        //verify if clientGstInfo collection is replaced with new collection
        verify(clientGstInfoRepository, times(1)).dropCollection();
        verify(clientGstInfoRepository, times(1)).putAll(anyCollection());

        //verify if gstAirlines collection is not replaced with new collection
        verify(gstAirlineRepository, times(0)).dropCollection();
        verify(gstAirlineRepository, times(0)).putAll(anyCollection());
    }

    @Test
    public void shouldNotSaveClientGstInfoIfNoneIsExtractedFromExcel() {
        GstAirline gstAirline = new GstAirline();
        GstLookup mockGstLookup = new GstLookup();
        mockGstLookup.setGstAirlines(Arrays.asList(gstAirline));
        mockGstLookup.setClientGstInfo(Collections.emptyList());

        when(clientGstInfoExcelReaderService.readExcelFile(any(), any())).thenReturn(mockGstLookup);

        service.saveFromExcelFile(inputStream, true);

        //verify if clientGstInfo collection is not replaced with new collection
        verify(clientGstInfoRepository, times(0)).dropCollection();
        verify(clientGstInfoRepository, times(0)).putAll(anyCollection());

        //verify if gstAirlines collection is replaced with new collection
        verify(gstAirlineRepository, times(1)).dropCollection();
        verify(gstAirlineRepository, times(1)).putAll(anyCollection());
    }

    @Test
    public void shouldNotSaveGstAirlinesIfNoneIsExtractedFromExcel() {
        ClientGstInfo clientGstInfo = new ClientGstInfo();
        GstLookup mockGstLookup = new GstLookup();
        mockGstLookup.setGstAirlines(Collections.emptyList());
        mockGstLookup.setClientGstInfo(Arrays.asList(clientGstInfo));

        when(clientGstInfoExcelReaderService.readExcelFile(any(), any())).thenReturn(mockGstLookup);

        service.saveFromExcelFile(inputStream, true);

        //verify if clientGstInfo collection is not replaced with new collection
        verify(clientGstInfoRepository, times(1)).dropCollection();
        verify(clientGstInfoRepository, times(1)).putAll(anyCollection());

        //verify if gstAirlines collection is replaced with new collection
        verify(gstAirlineRepository, times(0)).dropCollection();
        verify(gstAirlineRepository, times(0)).putAll(anyCollection());
    }
}
