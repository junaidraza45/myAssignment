package se.sbab.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusJourResponse {
    private String linenumber;
    private String directioncode;
    private String journeypatternpointnumber;
}
