package com.itau.devItau.exception;

public class TransactionBusinessException extends RuntimeException {
    public TransactionBusinessException(String message) {
        super(message);
    }
}
