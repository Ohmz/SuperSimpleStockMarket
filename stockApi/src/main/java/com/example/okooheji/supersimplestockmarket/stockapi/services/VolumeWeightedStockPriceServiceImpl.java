package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.TradeDao;
import com.example.okooheji.supersimplestockmarket.stockapi.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VolumeWeightedStockPriceServiceImpl implements VolumeWeightedStockPriceService {

    private final TradeDao tradeDao;
    private final TimeNowProvider timeNowProvider;

    @Autowired
    public VolumeWeightedStockPriceServiceImpl(TradeDao tradeDao, TimeNowProvider timeNowProvider) {
        this.tradeDao = tradeDao;
        this.timeNowProvider = timeNowProvider;
    }


    /**
     * Calculates the volume weighted stock price for a given stock in the last 15 minutes
     * @param symbol
     * @return
     */
    @Override
    public double calculateVolumeWeightedStockPrice(String symbol) {
        List<Trade> trades = tradeDao.getTradesWithinFifteenMinutesOfTimeStamp(symbol,timeNowProvider.getTimeNow());

        int totalValueTraded = 0;
        int totalStocksTraded = 0;
        for (Trade trade : trades) {
            // Assumption: The save trade service is the only mechanism to save a trade, otherwise we'd need to check for negative trades here
            // Could probably have made use of the streams api to do this as a lambda...
            totalValueTraded += trade.getPrice() * trade.getQuantity();
            totalStocksTraded += trade.getQuantity();
        }

        if (totalStocksTraded == 0 ) {
            return 0.0d; // Things will go badly wrong if we divide by zero.
        }

        return (double)totalValueTraded/(double)totalStocksTraded;
    }
}
