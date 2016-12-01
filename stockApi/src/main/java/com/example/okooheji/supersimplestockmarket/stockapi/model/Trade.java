package com.example.okooheji.supersimplestockmarket.stockapi.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class representing a single stock trade transaction
 */
public class Trade {

    private String symbol;
    private long timestamp;
    private int quantity;
    private int price;

    /**
     * Note I've not created a factory class for trades as they are not being read in from anywhere, thus invoking the constructor suffices.
     * @param symbol Stock Symbol
     * @param timestamp timestamp in ms since the epoch
     * @param quantity number of stocks traded
     * @param price unit price of the stocks traded
     */
    public Trade(String symbol, long timestamp, int quantity, int price) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Gets the ticker symbol of the stock
     * @return
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the timestamp the stock was saved
     * @return
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the quantity of stock traded in this transaction
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the price of an individual stock in the trade transaction
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the Trade transaction for logging/debug purpose
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
