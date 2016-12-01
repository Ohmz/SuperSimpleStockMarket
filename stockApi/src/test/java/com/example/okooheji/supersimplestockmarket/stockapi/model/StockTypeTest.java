package com.example.okooheji.supersimplestockmarket.stockapi.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StockTypeTest {

    private static final String COMMON = "Common";
    private static final String PREFERRED = "Preferred";

    @Test
    public void testParseCommon() {
        StockType stockType = StockType.parse(COMMON);
        assertEquals(StockType.Common, stockType);
    }

    @Test
    public void testParsePreferred() {
        StockType stockType = StockType.parse(PREFERRED);
        assertEquals(StockType.Preferred, stockType);
    }
}
