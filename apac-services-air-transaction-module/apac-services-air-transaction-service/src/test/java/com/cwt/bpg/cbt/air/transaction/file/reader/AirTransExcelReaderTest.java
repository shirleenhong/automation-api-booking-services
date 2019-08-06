package com.cwt.bpg.cbt.air.transaction.file.reader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;

public class AirTransExcelReaderTest
{
    @Test
    public void shouldReadExcel() throws IOException
    {
        String resourceName = "test.xlsx";
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(resourceName).getFile());
        FileInputStream inputStream = new FileInputStream(file);

        AirTransExcelReader reader = new AirTransExcelReader();
        List<AirTransaction> results = reader.parse(inputStream);

        AirTransaction airTrans = results.get(0);
        String bookingClass = airTrans.getBookingClasses().get(0);

        assertEquals("AA", airTrans.getAirlineCode());
        assertEquals("AMERICAN AIRLINES", airTrans.getAirlineDescription());
        assertEquals("AX", airTrans.getCcVendorCode());
        assertEquals("AMEX", airTrans.getCcVendorName());
        assertEquals("Airline", airTrans.getPassthroughType().getCode());
        assertEquals("A", bookingClass);
    }
}
