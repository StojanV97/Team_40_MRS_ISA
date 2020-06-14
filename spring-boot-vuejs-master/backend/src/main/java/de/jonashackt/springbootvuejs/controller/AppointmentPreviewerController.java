package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import de.jonashackt.springbootvuejs.service.AppointmentService;
import de.jonashackt.springbootvuejs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentPreviewerController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @GetMapping(value = "/appointment-preview/{id}")
    public ResponseEntity<Collection<AppointmentPreviewer>> getAppointmentPreview(@PathVariable long id) {

        Collection<AppointmentPreviewer> previewer = new ArrayList<AppointmentPreviewer>();
        Patient patient = new Patient();

        Optional<User> p =userRepository.findById(id);
        if(p.isPresent())
        {
            patient = (Patient) p.get();
        }

        ArrayList<Long>  appointments = patient.getAppointments();

        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();

        for(Long ids : appointments){
            AppointmentPreviewer appointmentPreviewer = new AppointmentPreviewer();
            Optional<Appointment> ap = appointmentRepository.findById(ids);
            if(ap.isPresent())
            {
                appointment = ap.get();
            }
            Optional<User> dc = userRepository.findById(appointment.getDoctorID());
            if(dc.isPresent())
            {
                doctor = (Doctor) dc.get();
            }

            Clinic clinic = clinicRepository.findById(appointment.getClinicID());

            appointmentPreviewer.setDoctorFirstName(doctor.getFirstName());
            appointmentPreviewer.setDoctorLastName(doctor.getLastName());

            appointmentPreviewer.setAppointmentType(appointment.getType());

            appointmentPreviewer.setDateAndTime(appointment.getDateAndTime());

            appointmentPreviewer.setClinicAddress(clinic.getAddress());
            appointmentPreviewer.setClinicName(clinic.getName());

            previewer.add(appointmentPreviewer);




        }


        return new ResponseEntity<Collection<AppointmentPreviewer>>(previewer, HttpStatus.OK);
    }
}


