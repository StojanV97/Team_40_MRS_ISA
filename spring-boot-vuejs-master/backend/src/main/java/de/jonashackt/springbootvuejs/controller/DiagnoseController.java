package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Diagnose;
import de.jonashackt.springbootvuejs.service.DiagnoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@RestController()
@RequestMapping("/api")
public class DiagnoseController {
    @Autowired
    private DiagnoseService diagnoseService;

    @PostMapping(value = "/diagnose/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createDiagnose(@RequestBody Diagnose diagnose) throws Exception {
        String s = diagnoseService.createDiagnose(diagnose);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(path = "/diagnose/delete/{id}")
    public ResponseEntity<String>  deleteDiagnose(@PathVariable long id) {
        String s = diagnoseService.deleteDiagnose(id);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "/diagnose/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Diagnose>> getAllDiagnoses() {
        Collection<Diagnose> diagnoses = diagnoseService.getAllDiasnoses();
        return new ResponseEntity<Collection<Diagnose>>(diagnoses, HttpStatus.OK);
    }

    @GetMapping(path = "/diagnose/{id}")
    public ResponseEntity<Diagnose> getDiagnoseById(@PathVariable("id") long id) {
        Diagnose d = diagnoseService.getDiagnose(id);
        return new ResponseEntity<Diagnose>(d, HttpStatus.OK);
    }
}
