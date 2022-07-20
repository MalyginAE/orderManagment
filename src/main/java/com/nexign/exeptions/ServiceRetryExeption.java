package com.nexign.exeptions;

public class ServiceRetryExeption extends RuntimeException {
    private final int statusCode;

    public ServiceRetryExeption(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
