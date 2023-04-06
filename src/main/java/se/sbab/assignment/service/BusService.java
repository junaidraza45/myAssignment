package se.sbab.assignment.service;

import se.sbab.assignment.db.model.Businformation;
import se.sbab.assignment.dto.BusStopsResponse;
import se.sbab.assignment.dto.TopBusLinesStopsResponse;

import java.util.Collection;
import java.util.List;


public interface BusService {

    BusStopsResponse findAllStopsForBusnumber(String busnumber);

    Collection<String> getAllBuses();

    void refresh(List<Businformation> businformationList);

    List<TopBusLinesStopsResponse> getBusLinesWithMaxnumberOfBusStops(int limit);

}
