package com.ecommerce.walletservice.exception;

public class LedgerRetrievalException extends RuntimeException{
    public LedgerRetrievalException() {
        super();
    }

    public LedgerRetrievalException(String message) {
        super(message);
    }
}
