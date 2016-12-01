package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;

/**
 * Calculates the dividend yield
 */
public interface DividendYieldService {

    /**
     * Calculates the dividend yeild for a given stock at a given price in pence
     * @param symbol
     * @param price
     * @return
     * @throws StockNotFoundException
     */
    double calculateDividendYield(String symbol, int price) throws StockNotFoundException;
}
