package com.itau.devItau.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String messageEx) {
        super(messageEx);
    }
}
