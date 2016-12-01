package com.example.okooheji.supersimplestockmarket.stockapi;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockApiTest {

    private static Logger logger = LoggerFactory.getLogger(StockApiTest.class);

    @Test
    public void test_integrationTest() throws StockNotFoundException {
        logger.debug("Stock API Integration test");
        StockApi stockApi = StockApiFactory.getStockApiInstance();
        double dy = stockApi.calculateDividendYield("TEA", 100);
        logger.debug("DY TEA 100 : {}", dy);
        double pe = stockApi.calculatePERatio("TEA", 100);
        logger.debug("P/E TEA 100 : {}", pe);
        stockApi.recordTrade("TEA",100,100);
        stockApi.recordTrade("GIN",50,40);
        stockApi.recordTrade("RUM",60,10);
        stockApi.recordTrade("RUM",50,20);
        stockApi.recordTrade("RUM",70,24);
        stockApi.recordTrade("RUM",60,23);
        logger.debug("Calculating VWSP");
        double vwspi = stockApi.calculateVolumeWeightedStockPriceInLastFifteenMinutes("RUM");
        logger.debug("VWSP = {}", vwspi);

        logger.debug("Calculating ASI");
        double asi = stockApi.calculateAllShareIndex();
        logger.debug("ASI= {}", asi);
    }
}
