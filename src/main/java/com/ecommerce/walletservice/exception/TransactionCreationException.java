package com.ecommerce.walletservice.exception;

public class TransactionCreationException extends RuntimeException{
    public TransactionCreationException() {
        super();
    }

    public TransactionCreationException(String message) {
        super(message);
    }
}
