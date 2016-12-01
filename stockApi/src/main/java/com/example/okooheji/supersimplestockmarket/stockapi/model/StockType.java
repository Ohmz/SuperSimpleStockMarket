package com.example.okooheji.supersimplestockmarket.stockapi.model;

/**
 * Enum used to differentiate two stock types.
 * Some calculations differ depending on the type of the stock.
 */
public enum StockType {
    Common,
    Preferred;

    public static StockType parse(String stockType) {
        return Enum.valueOf(StockType.class, stockType);
    }
}
