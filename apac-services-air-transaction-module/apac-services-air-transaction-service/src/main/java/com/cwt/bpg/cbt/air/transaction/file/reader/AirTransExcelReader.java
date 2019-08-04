package com.cwt.bpg.cbt.air.transaction.file.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.cwt.bpg.cbt.calculator.model.Country;

import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.*;

public class AirTransExcelReader
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AirTransExcelReaderTest.class);

    private static final int START_ROW = 7;

    public List<AirTransaction> parse(final String path) throws IOException
    {
        try (
                FileInputStream file = new FileInputStream(new File(path));
                Workbook workbook = new XSSFWorkbook(file);)
        {
            Sheet sheet = workbook.getSheetAt(0);
            List<AirTransaction> airTransactions = new ArrayList<>();
            sheet.forEach(r -> populate(r, airTransactions));
            
            return airTransactions;
        }
        catch (IOException e)
        {
            LOGGER.error("Error in parsing excel file", e);
        }
        return Collections.emptyList();
    }

    private void populate(Row row, List<AirTransaction> airTransactions)
    {
        int rowNumber = row.getRowNum();
        if (rowNumber >= START_ROW && StringUtils.isNotEmpty(row.getCell(AIRLINE_CODE).getStringCellValue()))
        {
            AirTransaction data = new AirTransaction();

            String airlinCode = StringUtils.trim(row.getCell(AIRLINE_CODE).getStringCellValue());
            data.setAirlineCode(airlinCode);
            
            String airlineDescription = StringUtils.trim(row.getCell(AIRLINE_DESCRIPTION).getStringCellValue()); 
            data.setAirlineDescription(airlineDescription);
            
            String vendorCode = StringUtils.trim(row.getCell(VENDOR_CODE).getStringCellValue());
            data.setCcVendorCode(vendorCode);
            
            String vendorName = StringUtils.trim(row.getCell(VENDOR_NAME).getStringCellValue());
            data.setCcVendorName(vendorName);
                       
            String passThruValue = row.getCell(PASS_THRU).getStringCellValue();
            data.setPassthroughType(PassthroughType.fromString(passThruValue));

            String bookingClasses = row.getCell(BOOKING_CLASSES).getStringCellValue();
            data.setBookingClasses(BookingClassParser.parse(bookingClasses));

            data.setCountryCode(Country.INDIA.getCode());

            airTransactions.add(data);
        }
    }
}
