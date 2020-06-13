package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import de.jonashackt.springbootvuejs.service.AppointmentService;
import de.jonashackt.springbootvuejs.service.ClinicService;
import de.jonashackt.springbootvuejs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@RestController()
@RequestMapping("/api")
public class ClinicController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserController userController;

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

    @GetMapping(path = "/clinics/get/{date}")
    public ResponseEntity<Collection<Clinic>> getClinicsForDate(@PathVariable("date") String date) {
        Collection<Clinic> clinics = clinicService.getAllClinics();


        Collection<Clinic> c = new ArrayList<Clinic>();
        ArrayList<Long> doctorsWithAppointments = new ArrayList<Long>();


        Collection<Appointment> allAppointments = appointmentService.getAllAppointments();

        Collection<Appointment> appointmentsForDate = new ArrayList<Appointment>();
        for(Appointment appointment : allAppointments)
        {
            //clinicID = appointment.getClinicID();
            if (appointment.getDateAndTime().split(" ")[0] == date)
            {
                appointmentsForDate.add(appointment);
                doctorsWithAppointments.add(appointment.getDoctorID());
            }
        }

        clinics: for(Clinic clinic :clinics){

            if(c.contains(clinic))
                    continue;

            Set<Doctor> doctors = userController.getDoctorsForClinic(clinic.getId());
            if(doctors.isEmpty()) {
                continue;
            }

            for(Doctor doctor: doctors)
            {

                if(doctorsWithAppointments.contains(doctor.getId()))
                {

                   if(Collections.frequency(doctorsWithAppointments,doctor.getId()) < 8)
                   {
                       c.add(clinic);
                       continue clinics;
                   }

                }
                else{
                    c.add(clinic);
                    continue clinics;
                }
            }

        }
        return new ResponseEntity<Collection<Clinic>>(c, HttpStatus.OK);
    }
}
