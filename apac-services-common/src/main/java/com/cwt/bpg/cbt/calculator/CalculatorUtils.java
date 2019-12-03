package com.cwt.bpg.cbt.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CalculatorUtils
{

    private CalculatorUtils()
    {
        throw new IllegalStateException("utility class");
    }

    public static String safeValue(String value)
    {
        return value == null ? "" : value;
    }

    public static BigDecimal safeValue(BigDecimal value)
    {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static Double safeValue(Double value)
    {
        return value == null ? 0D : value;
    }

    public static int safeValue(Integer value)
    {
        return value == null ? 0 : value;
    }

    public static BigDecimal round(BigDecimal amount, int scale)
    {
        return round(amount, scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal round(BigDecimal amount, int scale, RoundingMode roundingMode)
    {
        return amount == null ? null : amount.setScale(scale, roundingMode);
    }

    public static BigDecimal scale(BigDecimal amount, int scale)
    {
        return amount == null ? null : amount.setScale(scale, RoundingMode.DOWN);
    }

    public static BigDecimal calculatePercentage(BigDecimal input, Double percent)
    {
        return percentDecimal(safeValue(input).multiply(BigDecimal.valueOf(safeValue(percent))));
    }

    public static BigDecimal calculatePercentage(Double input, Double percent)
    {
        return BigDecimal.valueOf(percentDouble(safeValue(input) * safeValue(percent)));
    }

    public static Double percentDouble(Double value)
    {
        return safeValue(value) * 0.01;
    }

    public static BigDecimal percentDecimal(Double value)
    {
        return BigDecimal.valueOf(safeValue(value) * 0.01);
    }

    public static BigDecimal percentDecimal(BigDecimal value)
    {
        return safeValue(value).multiply(BigDecimal.valueOf(0.01));
    }
}
