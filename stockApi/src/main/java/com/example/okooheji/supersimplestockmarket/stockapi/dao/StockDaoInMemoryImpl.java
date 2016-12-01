package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.InvalidStockEntryException;
import com.example.okooheji.supersimplestockmarket.stockapi.model.Stock;
import com.example.okooheji.supersimplestockmarket.stockapi.model.StockFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * This is the implementation of a StockDao which stores the stock in memory
 * As a simplification is reads in the file it's self when it is created, however it would have been possible to have a
 * different class read in the stocks from a file and insert them into the DAO upon initialization.
 *
 * This would have necessitated a "saveStock" method. We could have then initialized the in memory stocks from any
 * data source like a database or a webservice call. this would have decoupled the DAO from the initialization.
 */
@Component
public class StockDaoInMemoryImpl implements StockDao{
    private static Logger logger = LoggerFactory.getLogger(StockDao.class);


    private Map<String,Stock> stockMap;


    private StockFactory stockFactory;

    /**
     * Constructor accepts a path and a factory to bootstrap the in memory list of stocks.
     * Could just have easily have defined a stock datasource interface that would pull stocks in from any datasource
     * but that seemed like overkill for a system this simple.
     * @param stockFileLocation
     * @param stockFactory
     * @throws InvalidStockEntryException
     * @throws FileNotFoundException
     */
    public StockDaoInMemoryImpl(String stockFileLocation, StockFactory stockFactory) throws InvalidStockEntryException, FileNotFoundException {
        logger.debug("Loading stocks from {}",stockFileLocation);
        this.stockFactory = stockFactory;
        stockMap = new HashMap<>(); // we are reading in from a file and we aren't editing this so it being synchronized doesn't matter.
        readInStocks(stockFileLocation, stockFactory);

    }

    /**
     * I chose to have the base stock data in a file, this private method is used to load in the data from the file.
     * See comment in class about how this could have been better decoupled in a real application.
     * @param stockFileLocation path to the file
     * @param stockFactory The factory class used to create the stocks, for different file formats different factories could be used.
     * @throws FileNotFoundException
     * @throws InvalidStockEntryException
     */
    private void readInStocks(String stockFileLocation, StockFactory stockFactory) throws FileNotFoundException, InvalidStockEntryException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(stockFileLocation).getFile());

        // Keep track of the line number for debugging purposes.
        // Assumption: that number of stocks does not exceed MAX_INT otherwise we'd use a long.
        int lineNumber = 1;
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                logger.debug("Parsing line {} '{}'", lineNumber,line);
                Stock stock = stockFactory.createStock(line);
                stockMap.put(stock.getSymbol(),stock);
                lineNumber++;
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("Failed to find file {} on classpath",stockFileLocation);
            throw fnfe; // there isn't much we can do if we can't find the file.

        } catch (InvalidStockEntryException e) {
            // Assumption is that if a single entry in the file if incorrect the whole thing is invalid
            // As an alternative could recover from this and parse the other lines and keep track of the lines with error on them.
            logger.error("Failed to parse stocks file error on line {}", lineNumber, e);
            throw e;
        }
    }

    /**
     * Retrieves and Optional of Type Stock for the given symbol, optional is empty if the stock is not present.
     * @param symbol Stock ticker sybol
     * @return
     */
    public Optional<Stock> getStock(String symbol) {
        if (this.stockMap.containsKey(symbol)) {
            return Optional.of(this.stockMap.get(symbol));
        }
        return Optional.empty();
    }
}
