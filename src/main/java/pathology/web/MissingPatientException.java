package pathology.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class MissingPatientException extends RuntimeException {
    MissingPatientException(Long patientId) {
        super(String.format("Could not find patient %s.", patientId));
    }
}
