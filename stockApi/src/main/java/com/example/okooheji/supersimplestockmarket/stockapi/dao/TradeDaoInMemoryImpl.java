package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import com.example.okooheji.supersimplestockmarket.stockapi.model.Trade;

import java.util.Collection;
import java.util.List;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Allows trades to be saved and retrieved, this class offers a simple in memory storage mechanism.
 */
public class TradeDaoInMemoryImpl implements TradeDao {

    Hashtable<String, List<Trade>> tradeRepository = new Hashtable<>();
    private static final long FIFTEEN_MINUTES = 1000 * 60 * 15;

    /**
     * Record a trade of either buy or sell along with the timestamp of the trade
     * @param symbol the Symbol of the stock being traded
     * @param timestamp I've opted to use a long representing ms since the epoch, could equally have used Date, Calendar or one of the JodaTime Date classes.
     * @param quantity the number of stocks traded
     * @param price the unit price at which the stock was traded.
     */
    public void recordTrade(String symbol, long timestamp, int quantity, int price)
    {
        Trade trade = new Trade(symbol,timestamp,quantity,price);
        recordTrade(trade);
    }

    /**
     * Records a trade provided a trade object.
      * @param trade
     */
    public void recordTrade(Trade trade) {
        synchronized (tradeRepository) {
            if(!tradeRepository.containsKey(trade.getSymbol())) {
                // Note there are more efficient ways to do this, and you might want to include a process that periodically removes stale trades to free up memory
                // Also using a sorted list would allow you to speed up your search at the cost of insertion speed
                tradeRepository.put(trade.getSymbol(),new ArrayList<>());
            }

            tradeRepository.get(trade.getSymbol()).add(trade);
        }
    }


    /**
     * Returns all the trades made in the 15 minutes window before the time stamp provided inclusive
     * Note: The assumption is that the timestamp is 'now' and as such trades made in the 'future' are also returned.
     * If this would cause issues the logic could be changed to not include trades after the timestamp
     * @param symbol
     * @return
     */
    public List<Trade> getTradesWithinFifteenMinutesOfTimeStamp(String symbol, long timeNow) {
        if (!tradeRepository.containsKey(symbol)) {
            return new ArrayList<>(); // Empty list
        }
        // This assumes the timestamp is now and any trades in the 'future' will also be counted the filter could be modified to not accept trades in the future though
        // given the spec though this will only be used to find trades within 15 minutes of "Now"
        return tradeRepository.get(symbol).stream().filter(trade -> trade.getTimestamp() >= (timeNow - FIFTEEN_MINUTES)).collect(Collectors.toList());
    }

    /**
     * Geta all trades for a given stock symbol
     * @param symbol
     * @return
     */
    @Override
    public List<Trade> getTrades(String symbol) {
        if (!tradeRepository.containsKey(symbol)) {
            return new ArrayList<>(); // Empty list
        }
        return tradeRepository.get(symbol);
    }

    /**
     * Returns a collection off the symbols that have trades.
     * @return
     */
    @Override
    public Collection<String> getSymbolsWithTrades() {
        return tradeRepository.keySet();
    }

    /**
     * Returns the last trade recorded for a given stock.
     * Assumption: trades are stored chronologically in the repository
     * Otherwise we'd have to find the trade with the max timestamp, or modify the repository so it used a sorted list or priority queue keyed on the timestamp
     * @param symbol
     * @return
     */
    @Override
    public Optional<Trade> getLastTradeForSymbol(String symbol) {
        if (!tradeRepository.containsKey(symbol)) {
            return Optional.empty();
        } else {
            List<Trade> tradesForSymbol = tradeRepository.get(symbol);
            if (tradesForSymbol.size() == 0) {
                // I don't see this happening with the current logic but future code changes might cause this to be an issue so best to guard against it.
                return Optional.empty();
            }
            // Last item in the list
            Trade trade = tradesForSymbol.get(tradesForSymbol.size() - 1);
            return Optional.of(trade); // pack it up
        }
    }

    /**
     * Returns the last trade for each stock in the datastore.
     * See assumptions for getLastTradeForSymbol();
     * @return
     */
    @Override
    public List<Trade> getLastTradeForEachStock() {
        Collection<String> symbols = getSymbolsWithTrades();
        List<Trade> trades = new ArrayList<>();
        for (String symbol : symbols) {
            Optional<Trade> tradeOptional = getLastTradeForSymbol(symbol);
            if (tradeOptional.isPresent()) {
                trades.add(tradeOptional.get());
            }
        }
        return trades;
    }

}
