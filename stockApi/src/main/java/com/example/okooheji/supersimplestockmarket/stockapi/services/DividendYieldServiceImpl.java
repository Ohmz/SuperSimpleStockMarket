package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.StockDao;
import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;
import com.example.okooheji.supersimplestockmarket.stockapi.model.Stock;
import com.example.okooheji.supersimplestockmarket.stockapi.model.StockType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Service to calculate the Dividend Yield.
 */
public class DividendYieldServiceImpl implements DividendYieldService {

    private static Logger logger = LoggerFactory.getLogger(DividendYieldServiceImpl.class);
    private final StockDao stockDao;

    @Autowired
    public DividendYieldServiceImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * Calculate the dividend yield for a given stock
     * It's assumed that no stock has 0 price otherwise the yield would be infinite
     *
     * Dividend Yield is calculated as follows:
     *
     * For Common Stocks:
     * Last Dividend / Price
     *
     * For Preferred Stocks:
     * (Fixed Dividend * Par Value) / Price
     *
     * @param symbol The symbol of the stock to be checked
     * @param price the price to check the stock
     * @return
     * @throws StockNotFoundException if the stock is not found
     */
    @Override
    public double calculateDividendYield(String symbol, int price) throws StockNotFoundException {
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
        if (stock.getType() == StockType.Common) {
            logger.debug("Calculating dividend yield for Common stock.");
            return (double)stock.getLastDividend()/(double)price;
        } else if (stock.getType() == StockType.Preferred) {
            logger.debug("Calculating dividend yield for Preferred stock");
            return stock.getFixedDividend() * stock.getParValue() / price;
        }

        // Assumption: It's better to catch this type of thing early, if someone added a third type of stock and didn't
        // add a dividend yield calculation we wouldn't know how to handle it.
        // An alternative would be to return 0 or to throw a caught exception.
        logger.error("Someone has added a new type of stock and not added a calculation for the dividend yield.");
        throw new RuntimeException();

    }
}
