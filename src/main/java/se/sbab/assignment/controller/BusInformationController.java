package se.sbab.assignment.controller;

import se.sbab.assignment.dto.BusStopsResponse;
import se.sbab.assignment.dto.TopBusLinesStopsResponse;
import se.sbab.assignment.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/v1/bus")
@Tag(name = "Bus Service Application", description = "Bus API V1")
@AllArgsConstructor
public class BusInformationController {

    BusService busServiceImpl;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint for Find all Buses", description = "", tags = {"Bus API V1"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public Collection<String> getAllBuses() {
        return busServiceImpl.getAllBuses();
    }


    @GetMapping(value = "/{busnumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint for Find all Bus stops for input Bus number", description = "", tags = {"Bus API V1"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public BusStopsResponse findBusStopsForGivenBusnumber(
            @Parameter(name = "busnumber", description = "busnumber to find all stops",
                    required = true)
            @PathVariable("busnumber") String busnumber) {
        return busServiceImpl.findAllStopsForBusnumber(busnumber);
    }


    @GetMapping(value = "/topbuslines", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint for Find top 10 BusLine with most number of bus stops", description = "", tags = {"Bus API V1"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public List<TopBusLinesStopsResponse> getTopBusLines() {
        return busServiceImpl.getBusLinesWithMaxnumberOfBusStops(10);
    }


}
