package com.cwt.bpg.cbt.tpromigration.csv;

import java.io.IOException;
import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
import com.cwt.bpg.cbt.tpromigration.csv.converter.AirMiscInfoConverter;

public class CSVReaderTest
{

    public static void main(String[] args) throws IOException
    {
        final CSVReader reader = new CSVReader();
        final List<AirMiscInfo> results = reader.parse("data/w1/airMiscInfo.csv", new AirMiscInfoConverter());

        System.out.println(results.size());
    }

}
