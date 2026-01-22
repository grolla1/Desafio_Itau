package com.itau.devItau.exception;

public class TransactionBusinessException extends RuntimeException {
    public TransactionBusinessException(String messageEx) {
        super(messageEx);
    }
}
