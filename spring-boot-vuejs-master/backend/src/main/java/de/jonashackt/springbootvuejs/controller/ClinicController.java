package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import de.jonashackt.springbootvuejs.repository.DaysOffRepository;
import de.jonashackt.springbootvuejs.service.AppointmentService;
import de.jonashackt.springbootvuejs.service.ClinicService;
import de.jonashackt.springbootvuejs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController()
@RequestMapping("/api")
public class ClinicController {

    @Autowired
    private DaysOffRepository daysOffRepository;

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

    @PostMapping(path = "/clinic/edit/{oldID}/{name}/{address}")
    public ResponseEntity<String> editClinic(@PathVariable String address,@PathVariable String name,@PathVariable long oldID) throws Exception {
        System.out.println(address);
        Clinic clinic2 = clinicService.getClinic(oldID);
        clinic2.setName(name);
        clinic2.setAddress(address);
        clinicRepository.save(clinic2);
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
    public ResponseEntity<Collection<Clinic>> getClinicsForDate(@PathVariable("date") String date) throws ParseException {
        Collection<Clinic> clinics = clinicService.getAllClinics();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));

        Collection<Clinic> c = new ArrayList<Clinic>();
        ArrayList<Long> doctorsWithAppointments = new ArrayList<Long>();


        Collection<Appointment> allAppointments = appointmentService.getAllAppointments();

        //Collection<Appointment> appointmentsForDate = new ArrayList<Appointment>();
        for(Appointment appointment : allAppointments)
        {
            //clinicID = appointment.getClinicID();
            String[] datum = appointment.getDateAndTime().split(" ");
            if (datum[0].equals(date))
            {

                //appointmentsForDate.add(appointment);
                doctorsWithAppointments.add(appointment.getDoctorID());
            }
        }

        clinics: for(Clinic clinic :clinics){

            if(c.contains(clinic))
                    continue;

            Set<Doctor> doctors = userController.getDoctorsForClinic(clinic.getId());
            System.out.println(doctors);
            if(doctors.isEmpty()) {
                continue;
            }

            for(Doctor doctor: doctors)
            {
                //DaysOff  daysOff = daysOffRepository.findByUserID(doctor.getId());
                //if(!(sp.parse(date).after(sp.parse(daysOff.getDateFrom())) && sp.parse(date).before(sp.parse(daysOff.getDateTo())))) {
                    if (doctorsWithAppointments.contains(doctor.getId())) {

                        if ((Collections.frequency(doctorsWithAppointments, doctor.getId())) < 9) {
                            c.add(clinic);
                            continue clinics;
                        }

                    }
                    else {
                        c.add(clinic);
                        continue clinics;
                    }
               // }

            }

        }
        return new ResponseEntity<Collection<Clinic>>(c, HttpStatus.OK);
    }
    @GetMapping(path = "/clinics/get/doctors/{date}/{id}")
    public ResponseEntity<Collection<Doctor>> getClinicDoctorForDate(@PathVariable("id") long id, @PathVariable("date") String date) throws ParseException {

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));


        Clinic clinic = clinicRepository.findById(id);
        Set<Doctor> doctors = userController.getDoctorsForClinic(clinic.getId());


        ArrayList<Long> doctorsWithAppointments = new ArrayList<>();
        Collection<Appointment> allAppointments = appointmentService.getAllAppointments();

        //Collection<Appointment> appointmentsForDate = new ArrayList<Appointment>();
        for(Appointment appointment : allAppointments)
        {
            String[] datum = appointment.getDateAndTime().split(" ");
            if (datum[0].equals(date))
            {

                //appointmentsForDate.add(appointment);
                doctorsWithAppointments.add(appointment.getDoctorID());
            }
        }

        Collection<Doctor> d = new ArrayList<Doctor>();
        for(Doctor doctor: doctors)
        {
            //DaysOff  daysOff = daysOffRepository.findByUserID(doctor.getId());
           // if(!(sp.parse(date).after(sp.parse(daysOff.getDateFrom())) && sp.parse(date).before(sp.parse(daysOff.getDateTo())))) {
                if (doctorsWithAppointments.contains(doctor.getId())) {
                    if ((Collections.frequency(doctorsWithAppointments, doctor.getId())) < 9) {
                        d.add(doctor);
                    }

                } else {
                    d.add(doctor);

                }
           // }
        }


        return new ResponseEntity<Collection<Doctor>>(d, HttpStatus.OK);
    }
}
