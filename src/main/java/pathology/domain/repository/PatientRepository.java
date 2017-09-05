package pathology.domain.repository;

import org.springframework.data.repository.CrudRepository;
import pathology.domain.Patient;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    List<Patient> findByLastName(String lastName);
}
