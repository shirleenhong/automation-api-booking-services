package com.cwt.bpg.cbt.air.transaction.file.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;

public class AirTransExcelReaderTest
{
    public static final void main(String[] args) throws IOException
    {
        String filePath = "/home/u021axc/Documents/New_UATP_Matrix v1.0 (002).xlsx";
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);

        AirTransExcelReader reader = new AirTransExcelReader();
        List<AirTransaction> airTrans = reader.parse(inputStream);

        for(AirTransaction air: airTrans) {
            System.out.println(air.getAirlineCode() + " " + air.getAirlineDescription() + " " + air.getCcVendorCode() + " " + air.getCcVendorName() + " " + air.getBookingClasses() + " " + air.getCountryCode());
        }
    }
}
