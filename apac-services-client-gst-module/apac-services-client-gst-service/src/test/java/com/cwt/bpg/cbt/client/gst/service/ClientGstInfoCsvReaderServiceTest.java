package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;

public class ClientGstInfoCsvReaderServiceTest {
	
    private InputStream testFileStream;
    
    private ClientGstInfoCsvReaderService service = new ClientGstInfoCsvReaderService();
    
    @Before
    public void setUp() throws IOException {
        testFileStream = new ClassPathResource("client_gst_info.csv").getInputStream();
    }
    
    @Test
    public void shouldReadCsvFile() throws Exception
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(testFileStream, false);
        assertTrue(clientGstInfo.size() == 5);

        ClientGstInfo info = clientGstInfo.get(0);
        assertEquals("RaNair@eagle.org", info.getBusinessEmailAddress());
        assertEquals("912241734173", info.getBusinessPhoneNumber());
        assertEquals("NaviMumbai", info.getCity());
        assertEquals("ABS Industrial Verification Pvt Ltd", info.getClientEntityName());
        assertEquals("ABS Industrial Verification (India) Private Limited", info.getClient());
        assertEquals("Lakhani Centrium 10th Floor", info.getEntityAddressLine1());
        assertEquals("Sec 15 CBD Belapur", info.getEntityAddressLine2());
        assertEquals("28AABCD4532R1ZD", info.getGstin());
        assertEquals("400614", info.getPostalCode());
        assertEquals("Maharashtra", info.getState());

    }
    
    @Test
    public void shouldReadCsvFileWithGstAirlines() throws Exception
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(testFileStream, false);
        assertTrue(clientGstInfo.size() == 5);
    }
    
    @Test
    public void shouldReadCsvFileWithError() throws Exception
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(null, false);
        assertNull(clientGstInfo);
    }

}
