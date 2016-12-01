package com.example.okooheji.supersimplestockmarket.stockapi.services;

/**
 * Allows a trade to be saved
 */
public interface TradeSaveService {

    /**
     * Saves a trade
     * @param symbol stock ticker symbol
     * @param timestamp time trade was saved in ms from the epoch
     * @param quantity number of stocks traded
     * @param price the price of an individual stock for this trade transaction
     */
    void saveTrade(String symbol, long timestamp, int quantity, int price);
}
