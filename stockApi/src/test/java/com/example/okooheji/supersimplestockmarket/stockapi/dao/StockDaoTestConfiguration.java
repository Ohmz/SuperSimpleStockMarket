package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.InvalidStockEntryException;
import com.example.okooheji.supersimplestockmarket.stockapi.model.StockFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

@Configuration
public class StockDaoTestConfiguration {

    @Bean
    public StockFactory stockFactory() {
        return new StockFactory();
    }

    @Bean
    public StockDao stockDao() throws FileNotFoundException, InvalidStockEntryException {
        return new StockDaoInMemoryImpl("stocks/stocks.csv", stockFactory());
    }

}
