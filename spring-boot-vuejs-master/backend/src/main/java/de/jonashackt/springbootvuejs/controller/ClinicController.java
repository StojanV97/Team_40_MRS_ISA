package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.domain.Doctor;
import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import de.jonashackt.springbootvuejs.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @PostMapping(path = "/clinic/edit/{oldID}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editClinic(@RequestBody Clinic clinic,@PathVariable long oldID) throws Exception {
        Clinic clinic2 = clinicService.getClinic(oldID);
        Clinic clinicNew = new Clinic();
        clinicNew.setId(clinic2.getId());
        clinicNew.setAdministrator(clinic2.getAdministrator());
        clinicNew.setName(clinic2.getName());
        clinicNew.setAddress(clinic2.getAddress());
        clinicNew.setId(clinic.getId());
        clinicNew.setDoctors(clinic2.getDoctors());
        clinicNew.setRooms(clinic2.getRooms());
        clinicService.deleteClinic(clinic2.getId());
        clinicService.createClinic(clinicNew);
        return new ResponseEntity<String>("sadas", HttpStatus.OK);
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
        return new ResponseEntity<Clinic>(c, HttpStatus.OK);
    }
}
