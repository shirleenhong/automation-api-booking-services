package com.cwt.bpg.cbt.air.transaction.file.reader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class BookingClassParserTest
{
    @Test
    public void shouldParseBookingClass()
    {
        String given = "W,H,M,E,B,Y,D,U,J,C,Z";
        List<String> bookingClasses = BookingClassParser.parse(given);
        assertFalse(bookingClasses.isEmpty());
    }

    @Test
    public void shouldHandleEmptyBookingClass()
    {
        String given = "";
        List<String> bookingClasses = BookingClassParser.parse(given);
        assertTrue(bookingClasses.isEmpty());
    }
    
    @Test
    public void shouldHandleSingleBookingClass()
    {
        String given = "A ";
        List<String> bookingClasses = BookingClassParser.parse(given);
        assertFalse(bookingClasses.isEmpty());
    }
    
    @Test
    public void shouldIgnoreALLValue()
    {
        String given = "ALL";
        List<String> bookingClasses = BookingClassParser.parse(given);
        assertTrue(bookingClasses.isEmpty());
    }
    
    @Test
    public void shouldIgnoreNAValue()
    {
        String given = "NA";
        List<String> bookingClasses = BookingClassParser.parse(given);
        assertTrue(bookingClasses.isEmpty());
    }
}
