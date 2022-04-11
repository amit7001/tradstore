package com.dbinterview.tradestore.exception;

public class TradeException extends RuntimeException {

    public TradeException(String message) {
        super(message);
    }

    public TradeException(Throwable throwable) {
        super(throwable);
    }

    public TradeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
