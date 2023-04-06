package se.sbab.assignment.exceptionhandler.exceptions;

import java.util.UUID;


public class BaseRunTimeException extends RuntimeException {
    String trackingCode;


    BaseRunTimeException() {
        this(null);
    }


    BaseRunTimeException(String message) {
        this(message, null);
    }


    BaseRunTimeException(String message, Throwable rootCause) {
        super(message, rootCause);
        this.trackingCode = UUID.randomUUID().toString();
    }


    public String getTrackingCode() {
        return trackingCode;
    }
}
