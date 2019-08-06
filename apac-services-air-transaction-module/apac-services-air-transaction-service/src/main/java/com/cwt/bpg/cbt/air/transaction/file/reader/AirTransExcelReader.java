package com.cwt.bpg.cbt.air.transaction.file.reader;

import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.AIRLINE_CODE;
import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.AIRLINE_DESCRIPTION;
import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.BOOKING_CLASSES;
import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.PASS_THRU;
import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.START_ROW;
import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.VENDOR_CODE;
import static com.cwt.bpg.cbt.air.transaction.file.reader.AirTransExcelColumn.VENDOR_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.monitorjbl.xlsx.StreamingReader.Builder;

@Component
public class AirTransExcelReader
{
    private static final int ROW_CACHE_SIZE = 100;
    private static final int BUFFER_SIZE = 4096;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AirTransExcelReader.class);

    public List<AirTransaction> parse(final InputStream inputStream) throws IOException
    {
        try (Workbook workbook = new Builder().rowCacheSize(ROW_CACHE_SIZE)
                                              .bufferSize(BUFFER_SIZE)
                                              .open(inputStream);)
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
        String airlinCode = getCellValue(row, AIRLINE_CODE);
        
        if (rowNumber >= START_ROW && StringUtils.isNotEmpty(airlinCode))
        {
            AirTransaction data = new AirTransaction();
            
            data.setCountryCode(Country.INDIA.getCode());
            data.setAirlineCode(airlinCode);

            String airlineDescription = getCellValue(row, AIRLINE_DESCRIPTION);
            data.setAirlineDescription(airlineDescription);

            String vendorCode = getCellValue(row, VENDOR_CODE);
            data.setCcVendorCode(vendorCode);

            String vendorName = getCellValue(row, VENDOR_NAME);
            data.setCcVendorName(vendorName);

            String passThruValue = getCellValue(row, PASS_THRU);
            data.setPassthroughType(PassthroughType.fromString(passThruValue));

            List<String> bookingClasses = BookingClassParser.parse(getCellValue(row, BOOKING_CLASSES));
            if(!CollectionUtils.isEmpty(bookingClasses)) {
                data.setBookingClasses(bookingClasses);
            }
            airTransactions.add(data);
        }
    }

    private String getCellValue(Row row, int index)
    {
        Cell cell = row.getCell(index);
        if (cell == null)
        {
            return null;
        }
        if (cell.getCellTypeEnum() == CellType.STRING)
        {
            return StringUtils.trim(cell.getStringCellValue());
        }
        return null;
    }
}
