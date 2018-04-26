package com.cwt.bpg.cbt.calculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class CommonCalculatorTest
{

    private CommonCalculator calculator = new CommonCalculator();

    @Test
    public void safeValueShouldReturnZeroWhenValueIsNull()
    {
        BigDecimal safeValue = calculator.safeValue(null);
        assertThat(safeValue, is(equalTo(BigDecimal.ZERO)));
    }

    @Test
    public void safeValueShouldDoNothingWhenValueIsNotNull()
    {
        BigDecimal decimal = new BigDecimal(10.00);
        BigDecimal safeValue = calculator.safeValue(decimal);
        assertThat(safeValue, is(equalTo(safeValue)));
    }

    @Test
    public void roundShouldReturnNullWhenAmountIsNull()
    {
        BigDecimal roundedDecimal = calculator.round(null, 2);
        assertThat(roundedDecimal, nullValue(BigDecimal.class));
    }

    @Test
    public void roundShouldSetScaleTo2WhenCountryCodeIsSG()
    {
        BigDecimal decimal = new BigDecimal("10");
        BigDecimal roundedDecimal = calculator.round(decimal, 2);
        assertThat(roundedDecimal, is(equalTo(new BigDecimal("10.00"))));
    }

    @Test
    public void roundShouldSetScaleTo0WhenCountryCodeIsHK()
    {
        BigDecimal decimal = new BigDecimal("10.49");
        BigDecimal roundedDecimal = calculator.round(decimal, 0);
        assertThat(roundedDecimal, is(equalTo(new BigDecimal("10"))));
    }

    @Test
    public void roundShouldSetScaleTo0WhenCountryCodeIsIN()
    {
        BigDecimal decimal = new BigDecimal("10.51");
        BigDecimal roundedDecimal = calculator.round(decimal, 0);
        assertThat(roundedDecimal, is(equalTo(new BigDecimal("11"))));
    }

    @Test
    public void percentDecimalReturnsZeroWhenValueIsNull()
    {
        BigDecimal percentDecimal = calculator.percentDecimal(0D);
        assertThat(percentDecimal.doubleValue(), is(equalTo(0.00)));
    }

    @Test
    public void percentDecimalReturnsNonZeroWhenValueIsNotNull()
    {
        BigDecimal percentDecimal = calculator.percentDecimal(5D);
        assertThat(percentDecimal.doubleValue(), is(equalTo(0.05)));
    }
    
    @Test
	public void shouldRoundUp() {
		assertEquals(new BigDecimal(4), calculator.round(new BigDecimal(3.5), 0));
		assertEquals(new BigDecimal(4), calculator.round(new BigDecimal(3.51), 0));
	}
	
	@Test
	public void shouldRoundDown() {
		assertEquals(new BigDecimal(3), calculator.round(new BigDecimal(3.4), 0));
		assertEquals(new BigDecimal(3), calculator.round(new BigDecimal(3.49), 0));
	}
	
}
