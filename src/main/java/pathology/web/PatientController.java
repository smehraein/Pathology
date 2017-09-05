package pathology.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pathology.domain.Patient;
import pathology.domain.repository.PatientRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientId}")
    Patient getPatient(@PathVariable Long patientId) {
        return Optional.ofNullable(this.patientRepository.findOne(patientId))
                .orElseThrow(() -> new MissingPatientException(patientId));
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createPatient(@RequestBody Patient input) {
        Patient patient = patientRepository.save(new Patient(input.getFirstName(), input.getLastName()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patient.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
