package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.GstLookup;

public class ClientGstInfoExcelReaderServiceTest
{
    private InputStream testFileStream;
    
    private ClientGstInfoExcelReaderService service = new ClientGstInfoExcelReaderService();
    
    @Before
    public void setUp() throws IOException {
        testFileStream = new ClassPathResource("client_gst_info.xlsx").getInputStream();
    }
    
    @Test
    public void shouldReadExcelFile()
    {
        GstLookup lookup = service.readExcelFile(testFileStream, false);
        assertTrue(lookup.getClientGstInfo().size() == 5);
        assertNull(lookup.getGstAirlines());
        
        ClientGstInfo info = lookup.getClientGstInfo().get(0);
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
        GstLookup lookup = service.readExcelFile(testFileStream, true);
        assertTrue(lookup.getClientGstInfo().size() == 5);
        assertTrue(lookup.getGstAirlines().size() == 48);

    }
    
    @Test
    public void shouldReadExcelFileWithError()
    {
        GstLookup lookup = service.readExcelFile(null, false);
        assertNull(lookup.getClientGstInfo());
        assertNull(lookup.getGstAirlines());
    }

}
