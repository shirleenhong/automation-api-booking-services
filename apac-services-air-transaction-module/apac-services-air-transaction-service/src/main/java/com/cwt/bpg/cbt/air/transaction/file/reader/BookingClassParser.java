package com.cwt.bpg.cbt.air.transaction.file.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class BookingClassParser
{
    private static final String DELIMETER = ",";
    private static final String BOOKING_CLASS_ALL = "all";
    
    private BookingClassParser() {}

    public static List<String> parse(String value)
    {
        if (StringUtils.isNoneEmpty(value) &&  !BOOKING_CLASS_ALL.equalsIgnoreCase(value))
        {
            List<String> bookingClasses = new ArrayList<>();
            String[] data = value.split(DELIMETER);

            for (String bookingClass : data)
            {
                bookingClasses.add(bookingClass.trim());
            }
            return bookingClasses;
        }
        return Collections.emptyList();
    }
}
