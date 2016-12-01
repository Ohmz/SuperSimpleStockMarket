package com.example.okooheji.supersimplestockmarket.stockapi;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;

/**
 * Created by okooheji on 12/1/16.
 */
public interface StockApi {
    double calculateDividendYield(String symbol, int price) throws StockNotFoundException;

    double calculatePERatio(String symbol, int price) throws StockNotFoundException;

    void recordTrade(String symbol, int quantity, int price);

    double calculateVolumeWeightedStockPriceInLastFifteenMinutes(String symbol);

    double calculateAllShareIndex();
}
