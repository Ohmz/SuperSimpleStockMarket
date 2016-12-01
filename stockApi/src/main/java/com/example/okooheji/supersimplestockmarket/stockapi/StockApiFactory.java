package com.example.okooheji.supersimplestockmarket.stockapi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class StockApiFactory {

    private static ApplicationContext context = new AnnotationConfigApplicationContext(StockApiConfiguration.class);
    public static StockApi getStockApiInstance() {
        return context.getBean(StockApi.class);
    }
}
