package com.cwt.bpg.cbt.air.transaction.file.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingClassParser
{
    private static final String DELIMETER = ",";
    private static final String BOOKING_CLASS_ALL = "all";

    public static List<String> parse(String value)
    {
        if (value != null &&  !BOOKING_CLASS_ALL.equalsIgnoreCase(value))
        {
            List<String> bookingClasses = new ArrayList<>();
            String[] data = value.split(DELIMETER);

            for (String bookingClass : data)
            {
                bookingClasses.add(bookingClass);
            }
            return bookingClasses;
        }
        return Collections.emptyList();
    }
}
