package com.cwt.bpg.cbt.services.rest.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;

import org.junit.Test;

public class TeePrintWriterTest
{

    @Test
    public void canWriteString()
    {
        final String s = "test";
        final int off = 1;
        final int len = 4;
        PrintWriter branch = mock(PrintWriter.class);
        PrintWriter main = mock(PrintWriter.class);

        TeePrintWriter tpw = new TeePrintWriter(main, branch);
        tpw.write(s, off, len);
        tpw.close();

        verify(branch, times(1)).write(s, off, len);
    }

    @Test
    public void canWriteChars()
    {
        final char[] s = new char[] { 't', 'p' };
        final int off = 1;
        final int len = 4;
        PrintWriter branch = mock(PrintWriter.class);
        PrintWriter main = mock(PrintWriter.class);

        TeePrintWriter tpw = new TeePrintWriter(main, branch);
        tpw.write(s, off, len);
        tpw.close();

        verify(branch, times(1)).write(s, off, len);
        verify(branch, times(1)).flush();
    }

    @Test
    public void canWriteInt()
    {
        final int i = 1;
        PrintWriter branch = mock(PrintWriter.class);
        PrintWriter main = mock(PrintWriter.class);

        TeePrintWriter tpw = new TeePrintWriter(main, branch);
        tpw.write(i);
        tpw.close();

        verify(branch, times(1)).write(i);
        verify(branch, times(1)).flush();
    }

    @Test
    public void canFlush()
    {
        PrintWriter branch = mock(PrintWriter.class);
        PrintWriter main = mock(PrintWriter.class);

        TeePrintWriter tpw = new TeePrintWriter(main, branch);
        tpw.flush();
        tpw.close();

        verify(branch, times(1)).flush();
    }

}
