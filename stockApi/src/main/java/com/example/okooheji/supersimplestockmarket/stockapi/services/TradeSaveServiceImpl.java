package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.TradeDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Allows trades to be saved
 */
public class TradeSaveServiceImpl implements TradeSaveService {

    private final TradeDao tradeDao;

    @Autowired
    public TradeSaveServiceImpl(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    /**
     * Saves a trade
      * @param symbol the symbol of the stock being traded
     * @param timestamp the time the trade took place
     * @param quantity the number of stocks traded
     * @param price the unit price of the stocks in pence
     */
    @Override
    public void saveTrade(String symbol, long timestamp, int quantity, int price) {
        if (quantity == 0 ) {
            return; // no sense in recording the trade.
        }

        if (quantity < 0) {
            // Someone is trying to screw around with our trades.
            throw new IllegalArgumentException("quantity");
        }
        tradeDao.recordTrade(symbol, timestamp, quantity, price);
    }
}
