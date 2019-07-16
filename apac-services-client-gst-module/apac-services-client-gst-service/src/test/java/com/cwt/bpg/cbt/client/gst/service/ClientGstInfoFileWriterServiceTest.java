package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.OrgType;
import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ClientGstInfoFileWriterServiceTest {

    private List<ClientGstInfo> clientGstInfo;
    private ClientGstInfoFileWriterService service;

    @Before
    public void setUp() {
        service = new ClientGstInfoFileWriterService();

        ClientGstInfo info1 = new ClientGstInfo();
        info1.setGstin("27AAACA4632R1ZD");
        info1.setClient("ABS Industrial Verification (India) Private Limited");
        info1.setClientEntityName("ABS Industrial Verification Pvt Ltd");
        info1.setBusinessPhoneNumber("912241734173");
        info1.setBusinessEmailAddress("RaNair@eagle.org");
        info1.setEntityAddressLine1("Lakhani Centrium 10th Floor");
        info1.setEntityAddressLine2("Sec 15 CBD Belapur");
        info1.setPostalCode("400614");
        info1.setCity("NaviMumbai");
        info1.setState("Maharashtra");
        info1.setOrgType(OrgType.S);

        ClientGstInfo info2 = new ClientGstInfo();
        info2.setGstin("27AAECA4872F1ZO");
        info2.setClient("ABS Professional Services India Pvt Ltd");
        info2.setClientEntityName("ABS proffesional services ");
        info2.setBusinessPhoneNumber("912066442804");
        info2.setBusinessEmailAddress("Absprofpune@eagle.org");
        info2.setEntityAddressLine1("Tech Park One Tower D");
        info2.setEntityAddressLine2("191 Yerwada");
        info2.setPostalCode("411 006");
        info2.setCity("Pune");
        info2.setState("Maharashtra");

        clientGstInfo = Arrays.asList(info1, info2);
    }

    @Test
    public void shouldWriteToFile() throws Exception{
        String expectedLine1 = "Free Format,GSTIN Numbers,Client Entity Name,Business Phone Number,"
                + "Business Email Address,Entity Address Line 1,Entity Address Line 1,Postal Code,City,State,";
        String expectedLine2 = "Client,FORMAT 15 Alpha numeric,FORMAT 35 Alpha Numeric,"
                + "FORMAT 15 Numeric (No Space or Hyphens etc),FORMAT 35 Alpha numeric,FORMAT 35 Alpha numeric,"
                + "FORMAT 25 Alpha Numeric,FORMAT 17 Alpha Numeric,FORMAT 25 Alpha,FORMAT 25 Alpha,OrgType";
        String expectedLine3 = "ABS Industrial Verification (India) Private Limited,27AAACA4632R1ZD,"
                + "ABS Industrial Verification Pvt Ltd,912241734173,RaNair@eagle.org,"
                + "Lakhani Centrium 10th Floor,Sec 15 CBD Belapur,400614,NaviMumbai,Maharashtra,S";
        String expectedLine4 = "ABS Professional Services India Pvt Ltd,27AAECA4872F1ZO,"
                + "ABS proffesional services ,912066442804,Absprofpune@eagle.org,"
                + "Tech Park One Tower D,191 Yerwada,411 006,Pune,Maharashtra,";

        WriteClientGstInfoFileResponse result = service.writeToFile(clientGstInfo);
        assertEquals(4, result.getLines().size());
        assertEquals(expectedLine1, result.getLines().get(0));
        assertEquals(expectedLine2, result.getLines().get(1));
        assertEquals(expectedLine3, result.getLines().get(2));
        assertEquals(expectedLine4, result.getLines().get(3));

        byte[] expectedBytes = combine(expectedLine1.getBytes(), System.lineSeparator().getBytes());
        expectedBytes = combine(expectedBytes, expectedLine2.getBytes());
        expectedBytes = combine(expectedBytes, System.lineSeparator().getBytes());
        expectedBytes = combine(expectedBytes, expectedLine3.getBytes());
        expectedBytes = combine(expectedBytes, System.lineSeparator().getBytes());
        expectedBytes = combine(expectedBytes, expectedLine4.getBytes());
        expectedBytes = combine(expectedBytes, System.lineSeparator().getBytes());

        assertArrayEquals(expectedBytes, result.getData());
    }

    private byte[] combine(byte[] a, byte[] b){
        int length = a.length + b.length;
        byte[] result = new byte[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}