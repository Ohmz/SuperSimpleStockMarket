package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import com.example.okooheji.supersimplestockmarket.stockapi.model.Stock;

import java.util.Optional;

/**
 * Data Access Object for Stocks, Stocks are read only in this example.
 *
 */
public interface StockDao {

    /**
     * Retrieves and Optional of Type Stock for the given symbol, optional is empty if the stock is not present.
     * @param symbol Stock ticker sybol
     * @return
     */
    Optional<Stock> getStock(String symbol) ;
}
