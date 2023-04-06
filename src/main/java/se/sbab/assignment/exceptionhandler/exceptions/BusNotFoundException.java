package se.sbab.assignment.exceptionhandler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND)
public class BusNotFoundException extends BaseRunTimeException {
    public BusNotFoundException(String message) {
        super(message);
    }


    public BusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
