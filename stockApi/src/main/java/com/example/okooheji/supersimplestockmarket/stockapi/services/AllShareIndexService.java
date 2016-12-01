package com.example.okooheji.supersimplestockmarket.stockapi.services;

/**
 * Allows the all share index to be calculated
 */
public interface AllShareIndexService {

    /**
     * Calculates the all share index for all the traded stocks
     * @return
     */
    double calculateAllShareIndex();
}
