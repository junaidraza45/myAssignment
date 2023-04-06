package se.sbab.assignment.service;

import se.sbab.assignment.db.model.Stopinformation;
import se.sbab.assignment.db.repository.StopInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StopServiceImpl implements StopService{

    private final StopInformationRepository stopInformationRepository;

    @Override
    public Optional<Stopinformation> getStopById(String id) {
        return stopInformationRepository.findById(id);
    }

    @Override
    public List<Stopinformation> getStopsById(List<String> stopIds) {
        return null;
    }

    @Override
    public void refresh(List<Stopinformation> stopInformation) {
        stopInformationRepository.deleteAll();
        stopInformationRepository.saveAll(stopInformation);
    }


}
