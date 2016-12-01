package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.StockDao;
import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;
import com.example.okooheji.supersimplestockmarket.stockapi.model.Stock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Service to calculate the P/E Ratio
 */
public class PERatioServiceImpl implements PERatioService {
    private static Logger logger = LoggerFactory.getLogger(PERatioServiceImpl.class);
    private final StockDao stockDao;

    @Autowired
    public PERatioServiceImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }


    /**
     * @return
     */


    /**
     * Calculates the P/E ratio for a given Stock given a price as input
     * @param symbol the symbol of the stock to be checked.
     * @param price in pence, no fractions of pence are allowed
     * @return the P/E ratio or 0.0 if the dividend is zero
     * @throws StockNotFoundException if we can't find the stock.
     */
    @Override
    public double calculatePERatio(String symbol, int price) throws StockNotFoundException {
        if (price == 0) {
            logger.error("Price was zero, can't calculate dividend yield"); // avoid divide by zero...
            throw new IllegalArgumentException("price");
        }

        if (StringUtils.isEmpty(symbol)) {
            logger.error("Stock wasn't provided.");
            throw new IllegalArgumentException("symbol");
        }

        Optional<Stock> stockOptional = stockDao.getStock(symbol);
        if (!stockOptional.isPresent()) {
            logger.error("Failed to find stock with symbol {}", symbol);
            throw new StockNotFoundException(symbol);
        }

        Stock stock = stockOptional.get();
        if (stock.getLastDividend() == 0) {
            // It's unclear what to do here, we could throw an exception or return 0.
            // I'm choosing to return 0
            return 0.0d;
        }

        // I'm making the assumption here that Dividend in the table refers to "Last Dividend"
        return (double)price/(double)stock.getLastDividend();
    }
}
