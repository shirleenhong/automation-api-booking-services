package com.cwt.bpg.cbt.services.rest.filter;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionLogTest
{

    private TransactionLog log;

    @Before
    public void init()
    {
        log = new TransactionLog("", "", "", 200, 100L, "");
    }

    @Test
    public void shouldParseTransactionLog()
    {
        log.parseUserIdentifiers(
                "UID: A:39ADF, Machine: L0PHMNL18031PWG, Application: PowerExpress, Version: 19.7.0.0, ClientCountryCode: IN, ClientTopUnitGUID: A:F5919, ClientSubUnitGUID: A:F591AWorkflowTransactionGUID: 314fc037-b7f1-41d2-884c-0eeb9d265d62");
        Assert.assertEquals(7, log.getUserIdentifiers().size());
    }

    @Test
    public void shouldTrim()
    {
        log.parseUserIdentifiers("UID: A:39ADF");
        Assert.assertEquals(1, log.getUserIdentifiers().size());

        Map.Entry<String, String> entry = log.getUserIdentifiers().entrySet().iterator().next();

        Assert.assertEquals("UID", entry.getKey());
        Assert.assertEquals("A:39ADF", entry.getValue());
    }

    @Test
    public void shouldIgnoreNoDelimeters()
    {
        log.parseUserIdentifiers("UID12345,Machine: L0PHMNL18031PWG");
        Assert.assertEquals(1, log.getUserIdentifiers().size());
    }

    @Test
    public void shouldIgnoreNoDelimeters2()
    {
        log.parseUserIdentifiers("UID12345Machine: L0PHMNL18031PWG");
        Assert.assertEquals(1, log.getUserIdentifiers().size());
    }

}
