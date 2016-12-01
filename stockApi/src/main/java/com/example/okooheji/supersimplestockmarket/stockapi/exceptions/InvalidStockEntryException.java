package com.example.okooheji.supersimplestockmarket.stockapi.exceptions;

/**
 * Exception class for failure to parse stock entries.
 * The message field of the Exception contains details on what went wrong.
 * In a more complex system it might be useful to have a parent exception and subclasses for each failure
 * That would allow us to use the class of the exception to decide how to handle it/recover
 * However in this instance that is overkill.
 * @author okooheji
 *
 */
public class InvalidStockEntryException extends Exception {

    /**
     * Constructor, the message is passed in so it can be displayed.
     * @param message
     */
    public InvalidStockEntryException(String message) {
        super(message);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
