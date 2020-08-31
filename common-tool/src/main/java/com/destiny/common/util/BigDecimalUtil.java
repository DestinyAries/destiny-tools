package com.destiny.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * BigDecimal Util
 * @Author Destiny
 * @Version 1.0.0
 */
public class BigDecimalUtil {
    /**
     * when {@code BigDecimal} is numerically greater than {@code val}
     * setting the result to a constant
     * when using {@link BigDecimal#compareTo}
     */
    public final static int GREATER_THAN = 1;
    /**
     * when {@code BigDecimal} is numerically equal to {@code val}
     * setting the result to a constant
     * when using {@link BigDecimal#compareTo}
     */
    public final static int EQUAL = 0;
    /**
     * when {@code BigDecimal} is numerically less than {@code val}
     * setting the result to a constant
     * when using {@link BigDecimal#compareTo}
     */
    public final static int LESS_THAN = -1;

    /**
     * DecimalFormat for money
     */
    private final static DecimalFormat AMOUNT_DECIMAL_FORMAT = new DecimalFormat("0.00");

    /**
     * get the formatted amount
     * example:
     * 1) 3.1 -> 3.10
     * 2) 0.3 -> 0.30
     * 3) 1.109 -> 1.10 (default rounding mode is cutting down the extra number)
     * 4) 100 -> 100.00
     *
     * @param decimal
     * @return
     */
    public static String getAmount(BigDecimal decimal) {
        return getAmount(decimal, BigDecimal.ROUND_DOWN);
    }

    /**
     * get the formatted amount
     * @param decimal
     * @return
     */
    public static String getAmount(BigDecimal decimal, int roundingMode) {
        Objects.requireNonNull(decimal);
        return AMOUNT_DECIMAL_FORMAT.format(decimal.setScale(2, roundingMode));
    }

}
