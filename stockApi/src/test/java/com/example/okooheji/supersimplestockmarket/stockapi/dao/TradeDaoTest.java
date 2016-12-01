package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TradeDaoTest {

    private static final long ONE_MINUTE = 1000 * 60;


    TradeDaoInMemoryImpl tradeDao;

    @Before
    public void initTradeDao() {
        tradeDao = new TradeDaoInMemoryImpl();
    }


    @Test
    public void test_recordTrade_BaseCase() {
        tradeDao.recordTrade("TEA", 1000000l, 6, 20);
        assertTrue(tradeDao.getSymbolsWithTrades().contains("TEA"));
        assertEquals(1, tradeDao.getTrades("TEA").size());
    }

    @Test
    public void test_getTradesWithinFifteenMinutes() {
        long timeStamp = 0l;
        // insert trades for the first 20 minutes of the epoc at a rate of one a minute
        for (int i = 0 ; i < 20 ; i++) {
            timeStamp = i * ONE_MINUTE;
            tradeDao.recordTrade("TEA", timeStamp, 5, 10);
        }

        assertEquals(20, tradeDao.getTrades("TEA").size());
        assertEquals(16, tradeDao.getTradesWithinFifteenMinutesOfTimeStamp("TEA", timeStamp).size()); // 16 because it's inclusive of current time.
    }

}
