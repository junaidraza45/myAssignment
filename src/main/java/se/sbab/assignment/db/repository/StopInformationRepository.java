package se.sbab.assignment.db.repository;


import se.sbab.assignment.db.model.Stopinformation;
import org.springframework.data.repository.CrudRepository;

public interface StopInformationRepository extends CrudRepository<Stopinformation, String> {
}
