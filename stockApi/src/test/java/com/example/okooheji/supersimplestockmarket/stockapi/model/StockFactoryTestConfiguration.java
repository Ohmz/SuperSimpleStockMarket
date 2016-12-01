package com.example.okooheji.supersimplestockmarket.stockapi.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockFactoryTestConfiguration {
    @Bean
    public StockFactory stockFactory() {
        return new StockFactory();
    }
}
