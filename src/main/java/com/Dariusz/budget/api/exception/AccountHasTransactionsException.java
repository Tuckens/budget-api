package com.Dariusz.budget.api.exception;

public class AccountHasTransactionsException extends RuntimeException{
    public AccountHasTransactionsException(String message) {
        super(message);
    }
}
