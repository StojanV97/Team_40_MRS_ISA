package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import de.jonashackt.springbootvuejs.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class ClinicController {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ClinicService clinicService;

    @PostMapping(value = "/clinic/register",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinic(@RequestBody Clinic clinic) throws Exception {
        String s = clinicService.createClinic(clinic);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(path = "/clinic/delete/{id}")
    public ResponseEntity<String>  deleteClinic(@PathVariable long id) {
        String s = clinicService.deleteClinic(id);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }
}
