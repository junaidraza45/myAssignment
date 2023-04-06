package se.sbab.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIError {
    @Getter
    @Setter
    String message;
    @Getter
    @Setter
    String trackingCode;


    public APIError(String message) {
        this.message = message;
    }


    public APIError(String message, String trackingCode) {
        this.message = message;
        this.trackingCode = trackingCode;
    }


}
