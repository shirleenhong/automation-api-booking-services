package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;

public class ReportHeaderServiceTest {

    @Mock
    private ReportHeaderRepository repository;

    @InjectMocks
    private ReportHeaderService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getReportHeaderShouldReturnReportHeader() {
        ReportHeader reportHeader = mock(ReportHeader.class);
        when(service.getHeaderReport("CWT SG")).thenReturn(reportHeader);

        ReportHeader result = service.getHeaderReport("CWT SG");

        assertNotNull(result);
        verify(repository, times(1)).get("CWT SG");
    }

    @Test
    public void saveShouldReturnSavedReportHeader() {
        ReportHeader ReportHeader = mock(ReportHeader.class);
        when(service.save(ReportHeader)).thenReturn(ReportHeader);

        ReportHeader result = service.save(ReportHeader);

        assertNotNull(result);
        verify(repository, times(1)).put(any(ReportHeader.class));
    }

    @Test
    public void deleteShouldReturnWriteResult() {
        when(service.delete(anyString())).thenReturn("result");

        String result = service.delete("CWT SG");

        assertNotNull(result);
        verify(repository, times(1)).remove(anyString());
    }
}
