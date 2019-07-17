package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;

public class ClientGstInfoExcelReaderServiceTest
{
    private InputStream xlsxFileStream;
    private InputStream xlsFileStream;
    
    private ClientGstInfoExcelReaderService service = new ClientGstInfoExcelReaderService();
    
    @Before
    public void setUp() throws IOException {
        xlsxFileStream = new ClassPathResource("client_gst_info.xlsx").getInputStream();
        xlsFileStream = new ClassPathResource("client_gst_info.xls").getInputStream();
    }
    
    @Test
    public void shouldReadXlsFile()
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(xlsFileStream);
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
    public void shouldReadXlsxFile()
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(xlsxFileStream);
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
    public void shouldReadExcelFileWithGstAirlines()
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(xlsxFileStream);
        assertTrue(clientGstInfo.size() == 5);
    }
    
    @Test
    public void shouldReadExcelFileWithError()
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(null);
        assertNull(clientGstInfo);
    }

}
