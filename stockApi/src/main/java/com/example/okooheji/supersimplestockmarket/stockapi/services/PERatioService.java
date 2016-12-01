package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;

/**
 * Calculates the P/E Ratio
 */
public interface PERatioService {
    /**
     * Calculates the P/E Ration for a given stock (Retrieved by ticker symbol) and a price in pence.
     * @param symbol
     * @param price
     * @return
     * @throws StockNotFoundException
     */
    double calculatePERatio(String symbol, int price) throws StockNotFoundException;
}
