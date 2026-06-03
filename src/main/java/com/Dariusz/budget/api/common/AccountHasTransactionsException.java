package com.Dariusz.budget.api.common;

public class AccountHasTransactionsException extends RuntimeException{
    public AccountHasTransactionsException(String message) {
        super(message);
    }
}
