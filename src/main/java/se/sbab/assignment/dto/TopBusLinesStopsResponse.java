package se.sbab.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopBusLinesStopsResponse {
    Integer srno;
    String busNumber;
    String stopsCount;
    String stopnames;

}
