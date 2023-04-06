package se.sbab.assignment.service;

import se.sbab.assignment.db.model.Businformation;
import se.sbab.assignment.db.repository.BusAndStopResults;
import se.sbab.assignment.db.repository.BusInformationRepository;
import se.sbab.assignment.db.repository.BusInformationRepository.TopBusResult;
import se.sbab.assignment.dto.BusAndStopResponse;
import se.sbab.assignment.dto.BusStopsResponse;
import se.sbab.assignment.dto.TopBusLinesStopsResponse;
import se.sbab.assignment.exceptionhandler.exceptions.BusNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class BusServiceImpl implements BusService {

    private final BusInformationRepository busInformationRepository;


    @Override
    public BusStopsResponse findAllStopsForBusnumber(String busnumber) {
        BusStopsResponse busStopsResponse = new BusStopsResponse();
        busStopsResponse.setBusnumber(busnumber);
        final Collection<BusAndStopResults> busNumberSearchResult = busInformationRepository.findBusStops(busnumber);
        busNumberSearchResult.stream().findAny().orElseThrow(() -> new BusNotFoundException(String.format("Bus no %s is not a valid busnumber", busnumber)));
        final Collection<BusAndStopResponse> stops = busNumberSearchResult.stream().map(this::convertBusDto).collect(Collectors.toList());
        busStopsResponse.setStops(stops);
        return busStopsResponse;
    }


    private BusAndStopResponse convertBusDto(BusAndStopResults busAndStopResults){
        return new BusAndStopResponse(busAndStopResults.getBusstopnumber(),busAndStopResults.getStopname());
    }

    // return list of unique bus numbers
    @Override
    public Collection<String> getAllBuses() {
        return (busInformationRepository.findAllUniqueBusNumbers());
    }


    private TopBusLinesStopsResponse convertToDto(TopBusResult topBusResult, Integer srno) {
        return new TopBusLinesStopsResponse(srno ,topBusResult.getBusNumber(), topBusResult.getStopCounts().toString(),findStopNameByBussNumber(topBusResult.getBusNumber()));
    }



    private String findStopNameByBussNumber(String bussNumber){
        List<String> stopName = busInformationRepository.findBussNamesByNumbers(bussNumber);
        return  stopName.stream().collect(Collectors.joining(" , "));
    }

    // intended to be called by schedulers
    @Override
    @Transactional
    public void refresh(List<Businformation> businformationList) {
        busInformationRepository.deleteAll();
        busInformationRepository.saveAll(businformationList);
    }


    // method is generic so user can decide how much results they want
    @Override
    public List<TopBusLinesStopsResponse> getBusLinesWithMaxnumberOfBusStops(int maxResult) {
        final List<TopBusResult> topBusNumbers = busInformationRepository.findTopBusNumbers();
        AtomicInteger index = new AtomicInteger(1);

        return topBusNumbers.stream()
                .map(topbusrecord -> convertToDto( topbusrecord ,index.getAndIncrement()))
                .limit(maxResult)
                .collect(Collectors.toList());



    }

}
