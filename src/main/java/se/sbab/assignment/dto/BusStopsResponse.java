package se.sbab.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Collection;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusStopsResponse {
    String busnumber;
    Collection<BusAndStopResponse> stops;

}
