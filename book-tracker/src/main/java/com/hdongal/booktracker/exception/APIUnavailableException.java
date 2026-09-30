package com.hdongal.booktracker.exception;

public class APIUnavailableException extends Exception {
    public APIUnavailableException(String message) {
        super(message);
    }

    public APIUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
