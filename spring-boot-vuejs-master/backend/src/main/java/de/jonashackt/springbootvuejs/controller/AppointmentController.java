package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.repository.*;
import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.service.RoomService;

import de.jonashackt.springbootvuejs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

    @Autowired
    ApointmentRepository apointmentRepository;
    @Autowired
    RoomService roomService;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    FreeAppointementsRepository freeAppointementsRepository;


    @GetMapping(value = "user/get-current-examinations/{userID}")
    public ResponseEntity<?> getExaminations(@PathVariable Long userID) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));
        Collection<Appointment> listOfAppointments = (Collection<Appointment>) apointmentRepository.findAll();
        ArrayList<Appointment> doctorsListOfAppointments = new ArrayList<Appointment>();
        ArrayList<Appointment> currentAppointments = new ArrayList<Appointment>();
        for(Appointment appointment : listOfAppointments){
            if(appointment.getDoctorID() == userID){
                doctorsListOfAppointments.add(appointment);
            }
        }
        if(!doctorsListOfAppointments.isEmpty()){
            for(Appointment appointment : doctorsListOfAppointments){
                Date examinationTerm = sp.parse(appointment.getDateAndTime());
                examinationTerm.setTime(examinationTerm.getTime() - (120  * 60000));
                Date endTerm = new Date(examinationTerm.getTime() + (30  * 60000));
                System.out.println(now);
                System.out.println(examinationTerm);
                System.out.println(endTerm);
                if(now.after(examinationTerm) && now.before(endTerm)){
                   currentAppointments.add(appointment);
                }
            }
        }

        return new ResponseEntity<ArrayList<Appointment>>(currentAppointments,HttpStatus.ACCEPTED);
    }




    @PostMapping(value = "appointment/cancel/{id}")
    public ResponseEntity<Boolean> cancelAppointment(@PathVariable long id) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));

        Optional<Appointment> ap = apointmentRepository.findById(id);
        Appointment appointment = new Appointment();
        if(ap.isPresent())
        {
            appointment = ap.get();
        }

        Patient patient = new Patient();
        Optional<User> p = userRepository.findById(appointment.getPatientID());
        if(p.isPresent())
        {
             patient = (Patient) p.get();
        }



        boolean cancelable = true;
        Date appointmentDate = sp.parse(appointment.getDateAndTime());
        Date current = new Date(System.currentTimeMillis());
        Date minus24 = new Date(appointmentDate.getTime() - 26*3600 * 1000);

        if(current.after(minus24))
        {

            cancelable = false;

        }else{
            apointmentRepository.delete(appointment);
            patient.getAppointments().remove(id);
            userRepository.save(patient);

        }
        return new ResponseEntity<>(cancelable,HttpStatus.ACCEPTED);
    }
    @PostMapping(value = "admin/predefined-appointements/{date}/{type}/{clinicID}/{patientID}/{doctorID}/{roomID}")
    public ResponseEntity<?> createPredefinedAppointment(@PathVariable String date,@PathVariable String type, @PathVariable Long clinicID,@PathVariable Long patientID,@PathVariable Long doctorID,@PathVariable Long roomID){
        FreeAppointements fr = new FreeAppointements(date,type,roomID,patientID,doctorID,clinicID);
        Collection<Appointment> appointements = (Collection<Appointment>) apointmentRepository.findAll();
        Collection<FreeAppointements> fappointements = (Collection<FreeAppointements>) freeAppointementsRepository.findAll();
        for(Appointment a : appointements){
            if (a.getDateAndTime().equalsIgnoreCase(fr.getDateAndTime())){
                return new ResponseEntity<String>("!OK", HttpStatus.OK);
            }
        }
        for(FreeAppointements fa : fappointements){
            if (fa.getDateAndTime().equalsIgnoreCase(fr.getDateAndTime()) && roomID == fa.getRoomID()){
                return new ResponseEntity<String>("!OK", HttpStatus.OK);
            }
        }

        freeAppointementsRepository.save(fr);
        return new ResponseEntity<String>("OK", HttpStatus.OK);

    }
    @GetMapping(value = "/admin/get-all-free-appoint/")
    public ResponseEntity<?> getAllPredef(){
        Collection<FreeAppointements> apt = (Collection<FreeAppointements>) freeAppointementsRepository.findAll();
        System.out.println(apt);
        return new ResponseEntity<Collection<FreeAppointements>>(apt,HttpStatus.OK);

    }


        @PostMapping(value = "admin/create-appoitnment/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAppoitment(@RequestBody Appointment appointment){
        ArrayList<String> termini = new ArrayList<String>();
        termini.add("10-00");
        termini.add("10-30");
        termini.add("11-00");
        termini.add("11-30");
        termini.add("12-00");
        termini.add("12-30");
        termini.add("13-00");
        termini.add("13-30");
        termini.add("14-00");
        String newTerm = "";
        Room room = roomService.getRoom(appointment.getRoomID());
       if(room.getCalendar().isEmpty()){
           newTerm = appointment.getDateAndTime() + " " + "10-00";
           appointment.setDateAndTime(appointment.getDateAndTime() + " 10-00");
       }else{
           ArrayList<String> busy = new ArrayList<String>();
           for(String s : room.getCalendar()){
               String[] splited = s.split(" ");
               if(splited[0].equalsIgnoreCase(appointment.getDateAndTime())){
                   busy.add(splited[1]);
               }
           }
           if(busy.isEmpty()){
               newTerm = appointment.getDateAndTime() + " " + "10-00";
               appointment.setDateAndTime(appointment.getDateAndTime() + " " + "10-00");
           }else{
                boolean flag = true;
                busyFor : for(String s : termini) {
                    if(busy.contains(s)){
                        System.out.println();
                    }else{
                        newTerm = appointment.getDateAndTime() + " " + s;
                        appointment.setDateAndTime(appointment.getDateAndTime() + " " + s);
                        break busyFor;
                    }
                }
           }
       }
        ArrayList<String> appt = new ArrayList<>(room.getCalendar());
        appt.add(newTerm);

        room.setCalendar(appt);
        roomRepository.save(room);
        Room newRoom  =  roomService.getRoom(room.getRoomID());


      Optional<User> d = userRepository.findById(appointment.getDoctorID());
      Doctor doctor = (Doctor) d.get();
      doctor.setListOfPatients(appointment.getPatientID());
      doctor.setListOfAppoitnements(newTerm);
      appointment.setClinicID(appointment.getClinicID());
      userRepository.save(doctor);
      apointmentRepository.save(appointment);
      AptIDandDate aptd = new AptIDandDate(newTerm,appointment.getId());
      //Treba dodati u listu kod pacijenta datum

        return new ResponseEntity<AptIDandDate>(aptd, HttpStatus.OK);
    }



}
