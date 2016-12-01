package com.example.okooheji.supersimplestockmarket.stockapi;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.StockDao;
import com.example.okooheji.supersimplestockmarket.stockapi.dao.StockDaoInMemoryImpl;
import com.example.okooheji.supersimplestockmarket.stockapi.dao.TradeDao;
import com.example.okooheji.supersimplestockmarket.stockapi.dao.TradeDaoInMemoryImpl;
import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.InvalidStockEntryException;
import com.example.okooheji.supersimplestockmarket.stockapi.model.StockFactory;
import com.example.okooheji.supersimplestockmarket.stockapi.services.AllShareIndexService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.AllShareIndexServiceImpl;
import com.example.okooheji.supersimplestockmarket.stockapi.services.DividendYieldService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.DividendYieldServiceImpl;
import com.example.okooheji.supersimplestockmarket.stockapi.services.PERatioService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.PERatioServiceImpl;
import com.example.okooheji.supersimplestockmarket.stockapi.services.TimeNowProvider;
import com.example.okooheji.supersimplestockmarket.stockapi.services.TradeSaveService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.TradeSaveServiceImpl;
import com.example.okooheji.supersimplestockmarket.stockapi.services.VolumeWeightedStockPriceService;
import com.example.okooheji.supersimplestockmarket.stockapi.services.VolumeWeightedStockPriceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

@Configuration
public class StockApiConfiguration {

    @Bean
    public StockFactory stockFactory() {
        return new StockFactory();
    }

    @Bean
    public StockDao stockDao() throws FileNotFoundException, InvalidStockEntryException {
        return new StockDaoInMemoryImpl("stocks/stocks.csv", stockFactory());
    }

    @Bean
    public TradeDao tradeDao() {
        return new TradeDaoInMemoryImpl();
    }

    @Bean
    public AllShareIndexService allShareIndexService() {
        return new AllShareIndexServiceImpl(tradeDao());
    }

    @Bean
    public DividendYieldService dividendYieldService() throws FileNotFoundException, InvalidStockEntryException {
        return new DividendYieldServiceImpl(stockDao());
    }

    @Bean
    public PERatioService peRatioService() throws FileNotFoundException, InvalidStockEntryException {
        return new PERatioServiceImpl(stockDao());
    }

    @Bean
    public TradeSaveService tradeSaveService() {
        return new TradeSaveServiceImpl(tradeDao());
    }

    @Bean
    public VolumeWeightedStockPriceService volumeWeightedStockPriceService() {
        return new VolumeWeightedStockPriceServiceImpl(tradeDao(),timeNowProvider());
    }

    @Bean
    public TimeNowProvider timeNowProvider() {
        return new TimeNowProvider();
    }

    @Bean
    StockApiImpl stockApi() throws FileNotFoundException, InvalidStockEntryException {
        return new StockApiImpl(allShareIndexService(),dividendYieldService(),peRatioService(),tradeSaveService(),volumeWeightedStockPriceService(),timeNowProvider());
    }
}
