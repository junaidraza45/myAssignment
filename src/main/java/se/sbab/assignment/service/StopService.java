package se.sbab.assignment.service;

import se.sbab.assignment.db.model.Stopinformation;

import java.util.List;
import java.util.Optional;

public interface StopService {

    Optional<Stopinformation> getStopById(String id);
    List<Stopinformation> getStopsById(List<String> stopIds);

    void refresh(List<Stopinformation> stopInformation);
}
