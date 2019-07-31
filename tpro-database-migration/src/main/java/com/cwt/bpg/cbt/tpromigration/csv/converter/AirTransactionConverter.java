package com.cwt.bpg.cbt.tpromigration.csv.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;

public class AirTransactionConverter implements Converter<Map<String, String>, AirTransaction>
{

    @Override
    public void convert(Map<String, String> source, List<AirTransaction> results)
    {
        final AirTransaction airTransaction = new AirTransaction();

        airTransaction.setAirlineCode(StringUtils.trim(source.get("airlineCode")));
        airTransaction.setAirlineDescription(StringUtils.trim(source.get("airlineDescription")));
        airTransaction.setCcVendorCode(StringUtils.trim(source.get("ccVendorCode")));
        airTransaction.setCcVendorName(StringUtils.trim(source.get("ccVendorName")));
        airTransaction.setCcType(StringUtils.trim(source.get("ccType")));
        airTransaction.setPassthroughType(PassthroughType.fromString(StringUtils.trim(source.get("passthroughType"))));
        airTransaction.setCountryCode(StringUtils.trim(source.get("countryCode")));
        airTransaction.setBookingClasses(getBookingClasses(StringUtils.trim(source.get("bookingClasses"))));

        results.add(airTransaction);
    }

    private List<String> getBookingClasses(String bookingClass)
    {
        if (StringUtils.isNotEmpty(bookingClass))
        {

            final String[] bookingClassValue = bookingClass.split(",");
            List<String> bookingClasses = new ArrayList<>();

            for (final String val : bookingClassValue)
            {
                bookingClasses.add(val);
            }

            return bookingClasses;

        }

        return Collections.emptyList();
    }
}
