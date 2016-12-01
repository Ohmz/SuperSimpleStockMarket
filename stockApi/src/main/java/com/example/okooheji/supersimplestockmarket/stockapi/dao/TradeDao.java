package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import com.example.okooheji.supersimplestockmarket.stockapi.model.Trade;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Data Access object for Trades, allows Trades to be stored and retrieved.
 * Provided implementation allows for trades to be stored in memory, but different implementations could use a database
 * or a web service etc.
 */
public interface TradeDao {
    void recordTrade(Trade trade);
    void recordTrade(String symbol, long timestamp, int quantity, int price);
    List<Trade> getTradesWithinFifteenMinutesOfTimeStamp(String symbol, long timeNow);
    List<Trade> getTrades(String symbol);
    Collection<String> getSymbolsWithTrades();
    Optional<Trade> getLastTradeForSymbol(String symbol);
    List<Trade> getLastTradeForEachStock();
}
