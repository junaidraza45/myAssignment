package se.sbab.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StopResponse {
    public Integer siteid;
    public String sitename;
    public String stopareanumber;
}
