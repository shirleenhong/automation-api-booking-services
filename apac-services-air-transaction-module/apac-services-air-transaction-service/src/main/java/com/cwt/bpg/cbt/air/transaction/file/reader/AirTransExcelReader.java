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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;
import com.cwt.bpg.cbt.calculator.model.Country;

@Component
public class AirTransExcelReader
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AirTransExcelReader.class);

    public List<AirTransaction> parse(final InputStream inputStream) throws IOException
    {
        try (Workbook workbook = new XSSFWorkbook(inputStream);)
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

            String airlinCode = StringUtils.trim(getCellValue(row, AIRLINE_CODE));
            data.setAirlineCode(airlinCode);

            String airlineDescription = StringUtils.trim(getCellValue(row, AIRLINE_DESCRIPTION));
            data.setAirlineDescription(airlineDescription);

            String vendorCode = StringUtils.trim(getCellValue(row, VENDOR_CODE));
            data.setCcVendorCode(vendorCode);

            String vendorName = StringUtils.trim(getCellValue(row, VENDOR_NAME));
            data.setCcVendorName(vendorName);

            String passThruValue = StringUtils.trim(getCellValue(row, PASS_THRU));
            data.setPassthroughType(PassthroughType.fromString(passThruValue));

            String bookingClasses = StringUtils.trim(getCellValue(row, BOOKING_CLASSES));
            data.setBookingClasses(BookingClassParser.parse(bookingClasses));

            data.setCountryCode(Country.INDIA.getCode());

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
            return cell.getStringCellValue();
        }
        return null;
    }
}
