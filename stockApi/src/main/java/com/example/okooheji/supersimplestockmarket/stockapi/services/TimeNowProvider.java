package com.example.okooheji.supersimplestockmarket.stockapi.services;

import java.util.Date;

/**
 * Provides a mechanism to allow me to set the time now for unit testing.
 */
public class TimeNowProvider {
    /**
     * Gets the time "now" IE when the method is invoked in ms from the Epoc.
     * @return
     */
    public long getTimeNow() {
        return new Date().getTime();
    }
}
