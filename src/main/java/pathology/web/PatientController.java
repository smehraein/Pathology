package pathology.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @RequestMapping("/")
    public String index() {
        return "HELLO!!!";
    }
}
