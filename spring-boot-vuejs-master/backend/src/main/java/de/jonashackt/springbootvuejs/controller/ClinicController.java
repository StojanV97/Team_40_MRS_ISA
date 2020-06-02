package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.domain.Doctor;
import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import de.jonashackt.springbootvuejs.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @PostMapping(value = "/clinic/regagain",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicAgain(@RequestBody Clinic clinic) throws Exception {
        String s = clinicService.createClinicAgain(clinic);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(path = "/clinic/delete/{id}")
    public ResponseEntity<String>  deleteClinic(@PathVariable long id) {
        String s = clinicService.deleteClinic(id);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping("/admin/get-clinic/{userName}")
    public Clinic getClinicForAdmin(@PathVariable String userName){
        return new Clinic();
    }

    @GetMapping(value = "/clinic/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Clinic>> getAllClinics() {
        Collection<Clinic> clinics = clinicService.getAllClinics();
        return new ResponseEntity<Collection<Clinic>>(clinics, HttpStatus.OK);
    }

    @GetMapping(path = "/clinic/{id}")
    public ResponseEntity<Clinic> getClinicById(@PathVariable("id") long id) {
        Clinic c = clinicService.getClinic(id);
        for(Room c1 : c.getRooms() ){
            System.out.println(c1);
        }

        for(Doctor d : c.getDoctors()){
            System.out.println("doktorjeovaj");
            System.out.println(d);
        }
        return new ResponseEntity<Clinic>(c, HttpStatus.OK);
    }
}
