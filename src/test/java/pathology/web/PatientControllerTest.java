package pathology.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pathology.domain.Patient;
import pathology.domain.repository.PatientRepository;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private static final String BASE_URL = "/patient/";

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        this.patientRepository.deleteAll();
    }

    @Test
    public void postNewPatient_returnsIsCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String patientJson = mapper.writeValueAsString(new Patient("John", "Doe"));
        this.mvc.perform(post(BASE_URL).contentType(contentType).content(patientJson)).andExpect(status().isCreated());
    }

    @Test
    public void getExistingPatient_returnsPatient() throws Exception {
        Patient patient = new Patient("John", "Doe");
        this.patientRepository.save(patient);
        this.mvc.perform(get(BASE_URL + patient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(patient.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(patient.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(patient.getLastName())));
    }

    @Test
    public void getNonExistingPatient_returnNotFound() throws Exception {
        this.mvc.perform(get(BASE_URL + 1234))
                .andExpect(status().isNotFound());
    }
}