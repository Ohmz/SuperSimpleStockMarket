package com.example.okooheji.supersimplestockmarket.stockapi.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class representing a simple stock
 *
 * Note: This class represents both Common and Preferred stocks,
 * I considered putting preferred in a separate subclass or having a shared interface but decided it was overkill.
 *
 * @author okooheji
 *
 */
public class Stock {
    private static final int DEFAULT_FIXED_DIVIDEND = 0;
    private final String symbol;
    private final int lastDividend;
    private final int parValue;
    private final StockType type;
    private final double fixedDividend;

    /**
     * Constructor to be used for 'Common' stocks
     * Note: constructor is protected, stocks should be created using the StockFactory
     * @param symbol
     * @param type
     * @param lastDividend
     * @param parValue
     */
    protected Stock(String symbol, StockType type, int lastDividend, int parValue) {
        this(symbol, type, lastDividend, DEFAULT_FIXED_DIVIDEND, parValue);
    }


    /**
     * Constructor to be used for 'Preferred' stocks
     * Note: constructor is protected, stocks should be created using the StockFactory
     * @param symbol
     * @param type
     * @param lastDividend
     * @param fixedDividend
     * @param parValue
     */
    protected Stock(String symbol, StockType type, int lastDividend, double fixedDividend , int parValue) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.type = type;
        this.parValue = parValue;
        this.fixedDividend = fixedDividend;
    }


    /**
     * Gets the ticker symbol for the stock,
     * the ticker is a unique identifier and no two companies will have the same ticker symbol.
     * @return ticker symbol as a string.
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Gets the stock type for the given stock
     * @return
     */
    public StockType getType() {
        return this.type;
    }

    /**
     * Gets the last dividend for this stock
     * Assumption that this value is in pence and there are no fractions of pence.
     * @return
     */
    public int getLastDividend() {
        return this.lastDividend;
    }

    /**
     * Gets the fixed dividend of the stock Fixed dividend is a percentage.
     * Not relevant to Common stocks.
     * @return
     */
    public double getFixedDividend() {
        return this.fixedDividend;
    }

    /**
     * Gets the Par Value of the stock
     * This value is in pence and the assumption is that there are no fractions of pence.
     * @return
     */
    public int getParValue(){
        return this.parValue;
    }

    /**
     * returns a string representation of the stock for debug and logging purposes.
     * @return
     */
    public String toString() {
        // To String used for logging. This could be made prettier.
        return ToStringBuilder.reflectionToString(this);
    }
}