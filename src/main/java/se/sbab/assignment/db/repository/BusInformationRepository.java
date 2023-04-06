package se.sbab.assignment.db.repository;

import se.sbab.assignment.db.model.Businformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface BusInformationRepository extends CrudRepository<Businformation, Long> {
    @Query(value ="SELECT BUSINFO.BUSSTOPNUMBER AS BUSSTOPNUMBER , STOPINFO.STOPNAME AS STOPNAME FROM BUSINFORMATION BUSINFO ,STOPINFORMATION STOPINFO " +
            "WHERE BUSINFO.BUSSTOPNUMBER = STOPINFO.STOPAREANUMBER AND BUSINFO.BUSNUMBER IN (:bussnumber)",nativeQuery = true)
    Collection<BusAndStopResults> findBusStops(@Param("bussnumber") String busnumber);




    @Query("SELECT distinct busnumber FROM Businformation")
    Collection<String> findAllUniqueBusNumbers();


    @Query(
            value = "SELECT BUSNUMBER , COUNT(BUSNUMBER) AS STOPCOUNTS  FROM BUSINFORMATION WHERE DIRECTIONCODE = 2 GROUP BY BUSNUMBER ORDER BY STOPCOUNTS DESC",
            nativeQuery = true)
    List<TopBusResult> findTopBusNumbers();


    interface TopBusResult {
        String getBusNumber();
        Integer getStopCounts();
    }


    @Query(value = "SELECT STOPINFO.STOPNAME FROM BUSINFORMATION BUSINFO ,STOPINFORMATION STOPINFO " +
                    "WHERE BUSINFO.BUSSTOPNUMBER = STOPINFO.STOPAREANUMBER AND BUSNUMBER IN (:bussnumber)",
            nativeQuery = true)
    List<String> findBussNamesByNumbers(@Param("bussnumber") String bussnumber);

}