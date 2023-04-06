package se.sbab.assignment.exceptionhandler.exceptions;

public class BadApiResponseException extends BaseRunTimeException {
    public BadApiResponseException(String message) {
        super(message);
    }


    public BadApiResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
