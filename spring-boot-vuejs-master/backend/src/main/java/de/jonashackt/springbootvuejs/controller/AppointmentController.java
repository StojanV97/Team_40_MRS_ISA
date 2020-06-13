package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @PostMapping(value = "admin/create-appoitnment/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAppoitment(@RequestBody Appointment appointment){
        System.out.println(appointment);
        appointmentRepository.save(appointment);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }



}
