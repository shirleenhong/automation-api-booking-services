package com.cwt.bpg.cbt.tpromigration.csv;

import java.io.IOException;

import com.cwt.bpg.cbt.tpromigration.csv.converter.AirMiscInfoConverter;

public class CSVReaderTest
{

    public static void main(String[] args) throws IOException
    {
        final CSVReader reader = new CSVReader();
        reader.parse("data/w1/airMiscInfo.csv", new AirMiscInfoConverter());
    }

}
