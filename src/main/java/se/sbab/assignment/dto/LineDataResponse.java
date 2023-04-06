package se.sbab.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineDataResponse {
    String StatusCode;
    List<BusJourResponse> busJourResponseList;
}
