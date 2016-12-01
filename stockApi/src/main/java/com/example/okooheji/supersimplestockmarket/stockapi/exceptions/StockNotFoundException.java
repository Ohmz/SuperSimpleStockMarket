package com.example.okooheji.supersimplestockmarket.stockapi.exceptions;

/**
 * Exception thrown when we are unable to find a given stock by it's ticker symbol.
 */
public class StockNotFoundException extends Throwable {
    public StockNotFoundException(String symbol) {
        super(String.format("Could not find stock with symbol %s", symbol));
    }
}
