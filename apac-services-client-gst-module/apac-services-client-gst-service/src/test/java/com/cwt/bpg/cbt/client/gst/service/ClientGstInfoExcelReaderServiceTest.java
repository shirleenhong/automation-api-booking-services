package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.exceptions.FileUploadException;

public class ClientGstInfoExcelReaderServiceTest
{
    private InputStream xlsxFileStream;
    
    private ClientGstInfoExcelReaderService service = new ClientGstInfoExcelReaderService();
    
    @Before
    public void setUp() throws IOException {
        xlsxFileStream = new ClassPathResource("client_gst_info.xlsx").getInputStream();
    }
    
    @Test
    public void shouldReadExcelFile() throws FileUploadException, IOException 
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(xlsxFileStream, false);
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
    public void shouldReadExcelFileWithGstAirlines() throws FileUploadException, IOException
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(xlsxFileStream, false);
        assertTrue(clientGstInfo.size() == 5);
    }
    
    @Test
    public void shouldReadExcelFileWithError() throws FileUploadException, IOException 
    {
        List<ClientGstInfo> clientGstInfo = service.readFile(null, false);
        assertTrue(CollectionUtils.isEmpty(clientGstInfo));
    }

}
