package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.MedicalRecord;
import de.jonashackt.springbootvuejs.repository.MedicalRecordRepository;
import de.jonashackt.springbootvuejs.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController()
@RequestMapping("/api")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping(path = "/medicalrecord/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable("id") long id) {
        MedicalRecord mr = medicalRecordService.getMedicalRecord(id);
        return new ResponseEntity<MedicalRecord>(mr, HttpStatus.OK);
    }

    @GetMapping(path = "/patient/medicalrecord/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecordForPatient(@PathVariable("id") long id)
    {
        MedicalRecord mr = medicalRecordRepository.findByPatientId(id);

        return new ResponseEntity<MedicalRecord>(mr, HttpStatus.OK);
    }
}
