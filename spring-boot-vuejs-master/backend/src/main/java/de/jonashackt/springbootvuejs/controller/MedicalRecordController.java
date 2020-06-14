package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.MedicalRecord;
import de.jonashackt.springbootvuejs.repository.MedicalRecordRepository;
import de.jonashackt.springbootvuejs.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/medicalrecord/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createMedicalRecord(@RequestBody MedicalRecord mr) throws Exception {
        String s = medicalRecordService.createMedicalRecord(mr);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(path = "/medicalrecord/delete/{id}")
    public ResponseEntity<String>  deleteMedicalRecord(@PathVariable long id) {
        String s = medicalRecordService.deleteMedicalRecord(id);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }
}
