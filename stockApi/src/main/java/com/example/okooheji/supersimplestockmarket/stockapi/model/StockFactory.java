package com.example.okooheji.supersimplestockmarket.stockapi.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.InvalidStockEntryException;
import org.springframework.stereotype.Component;

@Component
public class StockFactory {

    private static Logger logger = LoggerFactory.getLogger(StockFactory.class);

    /**
     * Accepts a comma separated line representing a stock from a file and creates the stock
     * This is the correct way to create a Stock object.
     *
     * Note: The stock factory is stateless but the factory methods are not static because this makes the class less testable,
     * and makes is harder to mock the class if required.
     *
     * I could create a stock factory interface and have this implement that so we could have other mechanisms of
     * creating stocks, or I could have implemented two factories one that created a stock from a line in a text file
     * and one using input parameters, but that seemed overkill...
     *
     * @param stockLineEntry
     * @return
     * @throws InvalidStockEntryException
     */
    public Stock createStock(String stockLineEntry) throws InvalidStockEntryException {
        if ( StringUtils.isEmpty(stockLineEntry)) {
            throw new InvalidStockEntryException("Null line item.");
        }

        String[] commaSeparatedValues = stockLineEntry.split(",");
        if (commaSeparatedValues.length != 5) {
            logger.error("Line entry contained too few items. Expected 5, Actual {}. Line {}",commaSeparatedValues.length,commaSeparatedValues);
            throw new InvalidStockEntryException("Not enough entries");
        }

        String symbol = commaSeparatedValues[0];
        StockType type = parseStockType(commaSeparatedValues[1]);
        int lastDividend = 	parseIntegerField(commaSeparatedValues[2], "Last Dividend");
        double fixedDividend = 0;
        if (type == StockType.Preferred) {
            if (StringUtils.isEmpty(commaSeparatedValues[3])){
                logger.error("Preferred stock with no fixed dividend");
                throw new InvalidStockEntryException("Preferred stock with no fixed dividend");
            }
            int fixedDividendPercentage = parseIntegerField(commaSeparatedValues[3], "Fixed Dividend");
            fixedDividend = (double)fixedDividendPercentage/100d;
        }
        int parValue = parseIntegerField(commaSeparatedValues[4], "Par Value");
        Stock stock = new Stock(symbol, type, lastDividend, fixedDividend, parValue);
        logger.debug("Created Stock: {}", stock);

        return stock;
    }

    /**
     * Helper method to create stocks for debugging purposes
     * @param symbol
     * @param type
     * @param lastDividend
     * @param fixedDividend
     * @param parValue
     * @return
     */
    public Stock createStock(String symbol, StockType type, int lastDividend, double fixedDividend , int parValue) {
        Stock stock = new Stock(symbol,type, lastDividend, fixedDividend, parValue);
        logger.debug("Created Stock: {}", stock);
        return stock;
    }

    /**
     * Parses an integer field from a string
     * @param value the string to be parsed
     * @param fieldName the field name, used primarilly for logging.
     * @return integer representing the value
     * @throws InvalidStockEntryException
     */
    private int parseIntegerField(String value, String fieldName)
            throws InvalidStockEntryException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            logger.error("Could not parse {} from {}", fieldName, value);
            throw new InvalidStockEntryException(String.format("Could not parse %s from %s",fieldName, value));
        }
    }

    /**
     * Parses the StockType from the string
     * @param typeAsString
     * @return StockType
     * @throws InvalidStockEntryException
     */
    private StockType parseStockType(String typeAsString)
            throws InvalidStockEntryException {
        try {
            return StockType.parse(typeAsString);
        } catch (IllegalArgumentException ex) {
            logger.error("Could not parse StockType from string '{}'", typeAsString);
            throw new InvalidStockEntryException(String.format("Could not parse StockType from string '%s'", typeAsString));
        }

    }
}
