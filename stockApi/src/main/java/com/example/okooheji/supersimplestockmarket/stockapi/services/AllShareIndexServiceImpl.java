package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.TradeDao;
import com.example.okooheji.supersimplestockmarket.stockapi.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Calculates the all share index for the stocks that have been traded.
 */
public class AllShareIndexServiceImpl implements AllShareIndexService {

    private static Logger logger = LoggerFactory.getLogger(AllShareIndexServiceImpl.class);

    TradeDao tradeDao;

    /**
     * Constructor allows the trade DAO to be set.
     * @param tradeDao
     */
    @Autowired
    public AllShareIndexServiceImpl(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    /**
     * Calculates the all share index for the stocks that have been traded
     *
     * The all share index is defined as the product of the prices of all the stocks, to the nth root where n is the number of prices.
     *
     * Assumptions:
     * 1. The price is the last price the stock was traded for (We could have used the volume weighted stock price for this as well, but that has issues with stocks that aren't commonly traded)
     * 2. Stocks with no trades are not counted in the calculation (Counting them as zero would mean that the entire product was 0 which would cause problems)
     * 3. All stocks have a positive price (This should be fairly obvious, they aren't given away or sold at a loss. Negative numbers don't play well with roots unless you want to get into imaginary numbers)
     *
     * @return
     */
    @Override
    public double calculateAllShareIndex() {
        List<Trade> trades = tradeDao.getLastTradeForEachStock();
        // Get the product of the trade prices;
        if (trades.size() == 0){
            return 0.0d;
        }
        int product = trades.stream().mapToInt(trade -> trade.getPrice()).reduce(1, (a,b)->a * b);
        logger.debug("Product is {}", product);
        logger.debug("Geometric mean = {}^(1/{})",product,trades.size());
        double geometricMean = Math.pow(product, 1.0d / trades.size());
        logger.debug("Geometric mean is {}", geometricMean);
        return geometricMean;
    }


}
