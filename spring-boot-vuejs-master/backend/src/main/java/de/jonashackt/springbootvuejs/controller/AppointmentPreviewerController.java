package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import de.jonashackt.springbootvuejs.repository.*;
import de.jonashackt.springbootvuejs.service.AppointmentService;
import de.jonashackt.springbootvuejs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private DaysOffRepository daysOffRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @GetMapping(value = "/appointment-preview/{id}")
    public ResponseEntity<Collection<AppointmentPreviewer>> getAppointmentPreview(@PathVariable long id) throws ParseException {

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));

        Collection<AppointmentPreviewer> previewer = new ArrayList<AppointmentPreviewer>();
        Patient patient = new Patient();

        Optional<User> p = userRepository.findById(id);
        if(p.isPresent())
        {
            patient = (Patient) p.get();
        }
        long pid = patient.getId();
        ArrayList<Appointment> allAppointments = (ArrayList<Appointment>) appointmentRepository.findAll();

        ArrayList<Long>  appointments = new ArrayList<Long>();
        Date today = new Date();
        for(Appointment app : allAppointments)
        {
            if(app.getPatientID() == pid){
                if(sp.parse(app.getDateAndTime()).after(today) || sp.parse(app.getDateAndTime()).equals(today))
                    appointments.add(app.getId());

            }
        }

        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();

        for(Long ids : appointments){
            AppointmentPreviewer appointmentPreviewer = new AppointmentPreviewer();
            appointmentPreviewer.setAppointmentID(ids);


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



            boolean cancelable = true;
            Date appointmentDate = sp.parse(appointment.getDateAndTime());
            Date minus24 = new Date(System.currentTimeMillis() - 24*3600 * 1000);
            if(appointmentDate.after(minus24))
            {
                cancelable = false;
            }


            appointmentPreviewer.setCancelable(cancelable);



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

    @GetMapping(value = "/appointment-preview-history/{id}")
    public ResponseEntity<Collection<AppointmentPreviewer>> getAppointmentPreviewHistory(@PathVariable long id) throws ParseException {

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));

        Collection<AppointmentPreviewer> previewer = new ArrayList<AppointmentPreviewer>();
        Patient patient = new Patient();

        Optional<User> p = userRepository.findById(id);
        if(p.isPresent())
        {
            patient = (Patient) p.get();
        }
        long pid = patient.getId();
        ArrayList<Appointment> allAppointments = (ArrayList<Appointment>) appointmentRepository.findAll();

        ArrayList<Long>  appointments = new ArrayList<Long>();
        Date today = new Date();
        for(Appointment app : allAppointments)
        {
            if(app.getPatientID() == pid){
                if(sp.parse(app.getDateAndTime()).before(today) )
                    appointments.add(app.getId());

            }
        }

        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();

        for(Long ids : appointments){
            AppointmentPreviewer appointmentPreviewer = new AppointmentPreviewer();
            appointmentPreviewer.setAppointmentID(ids);


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



            boolean cancelable = true;
            Date appointmentDate = sp.parse(appointment.getDateAndTime());
            Date minus24 = new Date(System.currentTimeMillis() - 24*3600 * 1000);
            if(appointmentDate.after(minus24))
            {
                cancelable = false;
            }


            appointmentPreviewer.setCancelable(cancelable);



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


