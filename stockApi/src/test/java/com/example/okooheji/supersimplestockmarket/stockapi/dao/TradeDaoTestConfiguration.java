package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TradeDaoTestConfiguration {

    @Bean
    TradeDao tradeDao() {
        return new TradeDaoInMemoryImpl();
    }
}
