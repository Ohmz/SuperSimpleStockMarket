package com.example.okooheji.supersimplestockmarket.stockapi;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;
import com.example.okooheji.supersimplestockmarket.stockapi.services.AllShareIndexService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.DividendYieldService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.PERatioService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.TimeNowProvider;
import com.example.okooheji.supersimplestockmarket.stockapi.services.TradeSaveService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.VolumeWeightedStockPriceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Api which allows several calculations to be performed on stocks
 */
public class StockApiImpl implements StockApi {

    private AllShareIndexService allShareIndexService;
    private DividendYieldService dividendYieldService;
    private PERatioService peRatioService;
    private TimeNowProvider timeNowProvider;
    private TradeSaveService tradeSaveService;
    private VolumeWeightedStockPriceService volumeWeightedStockPriceService;

    /**
     * I've opted for contructor injection of these dependencies, obviously this isn't the only way to do this.
     *
     * @param allShareIndexService
     * @param dividendYieldService
     * @param peRatioService
     * @param tradeSaveService
     * @param volumeWeightedStockPriceService
     * @param timeNowProvider
     */
    @Autowired
    public StockApiImpl(AllShareIndexService allShareIndexService, DividendYieldService dividendYieldService, PERatioService peRatioService, TradeSaveService tradeSaveService, VolumeWeightedStockPriceService volumeWeightedStockPriceService, TimeNowProvider timeNowProvider) {
        this.allShareIndexService = allShareIndexService;
        this.dividendYieldService = dividendYieldService;
        this.peRatioService = peRatioService;
        this.tradeSaveService = tradeSaveService;
        this.volumeWeightedStockPriceService = volumeWeightedStockPriceService;
        this.timeNowProvider = timeNowProvider;
    }

    /**
     * Calculates the dividend yield for a given stock and price
     * @param symbol
     * @param price
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public double calculateDividendYield(String symbol, int price) throws StockNotFoundException {
        return dividendYieldService.calculateDividendYield(symbol, price);
    }

    /**
     * Calculates the PERatio for a given stock and price
     * @param symbol
     * @param price
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public double calculatePERatio(String symbol, int price) throws StockNotFoundException {
        return peRatioService.calculatePERatio(symbol, price);
    }

    /**
     * Records a trade the timestamp is set to the time this method is called.
     * @param symbol
     * @param quantity
     * @param price
     */
    @Override
    public void recordTrade(String symbol, int quantity, int price) {
        tradeSaveService.saveTrade(symbol, timeNowProvider.getTimeNow(), quantity, price);
    }

    /**
     * Records a trade allows the time stamp to be specified.
     * NOTE in a real system this would be hidden behind an authorization layer or not included
     * @param symbol
     * @param quantity
     * @param timestamp
     * @param price
     */
    protected void recordTrade(String symbol, long timestamp, int quantity, int price) {
        tradeSaveService.saveTrade(symbol,timestamp,quantity,price);
    }

    /**
     * Calcultes the volume weighted stock price for a given stock for all trades in the last 15 minutes.
     * @param symbol
     * @return
     */
    @Override
    public double calculateVolumeWeightedStockPriceInLastFifteenMinutes(String symbol) {
        return volumeWeightedStockPriceService.calculateVolumeWeightedStockPrice(symbol);
    }

    /**
     * Calculates the all share index
     * @return
     */
    @Override
    public double calculateAllShareIndex() {
        return allShareIndexService.calculateAllShareIndex();
    }

}
