package com.destiny.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BigDecimalUtilTest {
    @Test
    public void getAmount() {
        Assertions.assertEquals("0.00", BigDecimalUtil.getAmount(new BigDecimal("0")));
        Assertions.assertEquals("-1.00", BigDecimalUtil.getAmount(new BigDecimal("-1")));
        Assertions.assertEquals("100.00", BigDecimalUtil.getAmount(new BigDecimal("100")));
        Assertions.assertEquals("-0.10", BigDecimalUtil.getAmount(new BigDecimal("-.1")));
        Assertions.assertEquals("0.10", BigDecimalUtil.getAmount(new BigDecimal(".1")));
        Assertions.assertEquals("0.10", BigDecimalUtil.getAmount(new BigDecimal(".109")));
        Assertions.assertEquals("999999999999999999999999999999999999999999999999999.10",
                BigDecimalUtil.getAmount(new BigDecimal("999999999999999999999999999999999999999999999999999.109")));
        Assertions.assertEquals("-999999999999999999999999999999999999999999999999999.10",
                BigDecimalUtil.getAmount(new BigDecimal("-999999999999999999999999999999999999999999999999999.109")));
    }
}
