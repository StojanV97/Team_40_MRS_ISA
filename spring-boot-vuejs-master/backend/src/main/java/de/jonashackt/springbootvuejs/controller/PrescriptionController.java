package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Prescription;
import de.jonashackt.springbootvuejs.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController()
@RequestMapping("/api")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping(value = "/prescription/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Prescription>> getAllPrescriptions() {
        Collection<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return new ResponseEntity<Collection<Prescription>>(prescriptions, HttpStatus.OK);
    }

    @GetMapping(path = "/prescription/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable("id") long id) {
        Prescription p = prescriptionService.getPrescription(id);
        return new ResponseEntity<Prescription>(p, HttpStatus.OK);
    }
}
