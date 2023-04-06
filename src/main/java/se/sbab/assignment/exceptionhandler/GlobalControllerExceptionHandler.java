package se.sbab.assignment.exceptionhandler;

import se.sbab.assignment.dto.APIError;
import se.sbab.assignment.exceptionhandler.exceptions.BaseRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BaseRunTimeException.class)
    protected ResponseEntity handleKnownException(
            BaseRunTimeException baseException, WebRequest request) {

        APIError apiError = new APIError(baseException.getMessage(), baseException.getTrackingCode());

        log.warn("API Exception handler {} {}", baseException.getTrackingCode(), baseException.getMessage());
        if (baseException.getCause() != null) {
            log.warn("API Exception handler {} caused by {}", baseException.getTrackingCode(), baseException.getCause().getMessage());
        }

        if (baseException.getClass().isAnnotationPresent(ResponseStatus.class)) {
            HttpStatus status = baseException.getClass().getAnnotation(ResponseStatus.class).code();

            return handleExceptionInternal(baseException, apiError,
                    new HttpHeaders(), status, request);
        } else {

            return handleExceptionInternal(baseException, apiError,
                    new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }


    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity handleUnknownException(
            RuntimeException ex, WebRequest request) {

        log.error("API Exception handler found unknown error", ex);

        if (ex instanceof IllegalArgumentException) {
            return handleExceptionInternal(ex, new APIError("You sent something I don't understand, check your request"),
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        return handleExceptionInternal(ex, new APIError("Something bad happened."),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}