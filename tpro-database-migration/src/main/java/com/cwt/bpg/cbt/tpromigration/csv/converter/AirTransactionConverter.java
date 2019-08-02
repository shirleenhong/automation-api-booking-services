package com.cwt.bpg.cbt.tpromigration.csv.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;

public class AirTransactionConverter implements Converter<Map<String, String>, AirTransaction>
{
    private static final String BOOKING_CLASS_INVALID_VALUE = "ALL";
    
    @Override
    public void convert(Map<String, String> source, List<AirTransaction> results)
    {
        final AirTransaction airTransaction = new AirTransaction();

        airTransaction.setAirlineCode(StringUtils.trim(source.get("airlineCode")));
        airTransaction.setAirlineDescription(StringUtils.trim(source.get("airlineDescription")));
        airTransaction.setCcVendorCode(StringUtils.trim(source.get("ccVendorCode")));
        airTransaction.setCcVendorName(StringUtils.trim(source.get("ccVendorName")));
        airTransaction.setPassthroughType(PassthroughType.fromString(StringUtils.trim(source.get("passthroughType"))));
        airTransaction.setBookingClasses(getBookingClasses(StringUtils.trim(source.get("bookingClasses"))));
        airTransaction.setCountryCode(StringUtils.trim(source.get("countryCode")));
        
        results.add(airTransaction);
    }

    private List<String> getBookingClasses(String bookingClass)
    {
        if (StringUtils.isNotEmpty(bookingClass) && !BOOKING_CLASS_INVALID_VALUE.equalsIgnoreCase(bookingClass))
        {

            final String[] bookingClassValue = bookingClass.split(",");
            List<String> bookingClasses = new ArrayList<>();

            for (final String val : bookingClassValue)
            {
                bookingClasses.add(val);
            }

            return bookingClasses;
        }
        return null;
    }
}
