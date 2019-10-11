package com.cwt.bpg.cbt.air.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionBackupException;
import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelReader;
import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;

public class AirTransactionServiceTest {

    @Mock
    private AirTransactionService proxy;

    @Mock
    private AirTransactionRepository repository;
    
    @Mock
    private AirTransactionBackupService backupService;
    
    @Mock
    private AirTransExcelReader excelReader;

    @InjectMocks
    private AirTransactionService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAirTransactionListShouldReturnAirTransactionList() {
		@SuppressWarnings("unchecked")
        List<AirTransaction> airTransactions = mock(List.class);
        when(repository.getAirTransactions(any(AirTransactionInput.class))).thenReturn(airTransactions);

        List<AirTransaction> result = service.getAirTransactionList(new AirTransactionInput());

        assertEquals(airTransactions, result);
        verify(repository, times(1)).getAirTransactions(any(AirTransactionInput.class));
    }

    @Test
    public void getAirTransactionShouldReturnAirTransaction() throws AirTransactionNoContentException {
        AirTransaction airTransaction = mock(AirTransaction.class);
        when(airTransaction.getPassthroughType()).thenReturn(PassthroughType.CWT);

        List<AirTransaction> airTransactions = new ArrayList<>();
        airTransactions.add(airTransaction);

        when(proxy.getAirTransactionList(any(AirTransactionInput.class))).thenReturn(airTransactions);

        AirTransactionOutput result = service.getAirTransaction(new AirTransactionInput());

        assertNotNull(result);
        assertEquals(PassthroughType.CWT, result.getPassthroughType());
        verify(proxy, times(1)).getAirTransactionList(any(AirTransactionInput.class));
    }
    
    @Test(expected = AirTransactionNoContentException.class)
    public void getAirTransactionListShouldThrowException() throws AirTransactionNoContentException {
    	AirTransaction airTransaction = mock(AirTransaction.class);
        when(airTransaction.getPassthroughType()).thenReturn(PassthroughType.CWT);

        List<AirTransaction> airTransactions = new ArrayList<>();

        when(proxy.getAirTransactionList(any(AirTransactionInput.class))).thenReturn(airTransactions);

        AirTransactionOutput result = service.getAirTransaction(new AirTransactionInput());

        assertNotNull(result);
        assertEquals(PassthroughType.CWT, result.getPassthroughType());
        verify(proxy, times(1)).getAirTransactionList(any(AirTransactionInput.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void saveShouldReturnSavedAirTransaction() {
        when(repository.putAll(any(List.class))).thenReturn(Arrays.asList(new AirTransaction()));

        service.save(Arrays.asList(new AirTransaction()));

        verify(repository, times(1)).putAll(any(List.class));
    }

    @Test
    public void deleteShouldReturnWriteResult() {
    	String id = "5b2870d6284b8d1ac84300ad";
        when(repository.remove(any(ObjectId.class))).thenReturn(id);

        String result = service.delete(id);

        assertEquals(id, result);
        verify(repository, times(1)).remove(any(ObjectId.class));
    }
    
    @Test
    public void shouldUploadExcel() throws IOException, AirTransactionBackupException {
        InputStream inputStream = mock(InputStream.class);
        String fileType = "xlsx";
        
        when(excelReader.parse(any(InputStream.class))).thenReturn(Arrays.asList(new AirTransaction()));
        when(repository.putAll(any())).thenReturn(Arrays.asList(new AirTransaction()));
        doNothing().when(backupService).archive(any(), any());
        doNothing().when(repository).dropCollection();
        
        service.upload(inputStream, fileType);
        
        verify(excelReader, times(1)).parse(any(InputStream.class));
        verify(repository, times(1)).putAll(any());
        verify(backupService, times(1)).archive(any(), any());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldHandleInvalidFile() throws AirTransactionBackupException {
        InputStream inputStream = mock(InputStream.class);
        String fileType = "xxx";
        
        service.upload(inputStream, fileType);
    }
    
    @Test( expected = AirTransactionBackupException.class )
    public void shouldHandleErrorInParsing() throws IOException, AirTransactionBackupException {
        InputStream inputStream = mock(InputStream.class);
        String fileType = "xlsx";
        
        when(excelReader.parse(any(InputStream.class))).thenThrow(new IOException());
        service.upload(inputStream, fileType);
    }
}
