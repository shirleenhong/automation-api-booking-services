package com.cwt.bpg.cbt.air.transaction.file.reader;

import java.io.IOException;
import java.util.List;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;

public class AirTransExcelReaderTest
{
    public static final void main(String[] args) throws IOException
    {
        String file = "/home/u021axc/Documents/New_UATP_Matrix v1.0 (002).xlsx";

        AirTransExcelReader reader = new AirTransExcelReader();
        List<AirTransaction> airTrans = reader.parse(file);

        for(AirTransaction air: airTrans) {
            System.out.println(air.getAirlineCode() + " " + air.getAirlineDescription() + " " + air.getCcVendorCode() + " " + air.getCcVendorName() + " " + air.getBookingClasses() + " " + air.getCountryCode());
        }
    }
}
