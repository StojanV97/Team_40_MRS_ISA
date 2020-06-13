package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.AppointmentRequest;
import de.jonashackt.springbootvuejs.domain.AppointmentRequest;
import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import de.jonashackt.springbootvuejs.repository.AppointmentRequestRepository;
import de.jonashackt.springbootvuejs.repository.RequestRepository;
import de.jonashackt.springbootvuejs.service.AppointmentRequestService;
import de.jonashackt.springbootvuejs.service.ClinicService;
import de.jonashackt.springbootvuejs.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController()
@RequestMapping("/api")
public class RequestController {


    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private AppointmentRequestRepository appointmentRequestRepository;
    @Autowired
    private RequestService requestService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private RequestRepository requestRepository;

    @PostMapping(value = "/auth/registration",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRequest(@RequestBody RegisterRequests request) throws Exception {
        String s = requestService.createRequest(request);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }


    @GetMapping(value = "requests/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegisterRequests>> getGreetings() {
        Collection<RegisterRequests> requests = requestService.getAllRequests();
        return new ResponseEntity<Collection<RegisterRequests>>(requests, HttpStatus.OK);
    }


    @PostMapping(value = "request/delete/{userName}")
    private ResponseEntity<String> deleteRoom(@PathVariable String userName) {
        String s = requestService.delete(userName);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "admin/get-appointment-requests/{clinicID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAppointmentRequests(@PathVariable Long clinicID) {
        Collection<AppointmentRequest> listOfRequests = appointmentRequestService.getAllRequests();
        ArrayList<AppointmentRequest> requestsForClinic = new ArrayList<AppointmentRequest>();
        for (AppointmentRequest ap : listOfRequests) {
            if (ap.getClinicID() == clinicID) {
                requestsForClinic.add(ap);
            }
        }
        return new ResponseEntity<ArrayList<AppointmentRequest>>(requestsForClinic, HttpStatus.OK);
    }

    @GetMapping(value = "admin/get-first-dates/{clinicID}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFirstDates(@PathVariable Long clinicID, @PathVariable String date) throws ParseException {
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
        final long ONE_MINUTE_IN_MILLIS = 60000;
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));
        Clinic c = clinicService.getClinic(clinicID);

        for (Room r : c.getRooms()) {
            if (!r.getCalendar().isEmpty()) {
                ArrayList<Date> listOfDates = new ArrayList<Date>();
                for (String s : r.getCalendar()) {
                    Date d = sp.parse(s);
                    Date d2 = new Date(d.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                    listOfDates.add(d);
                }
                Collections.sort(listOfDates);
                Date current = new Date();
                String currentDate = sp.format(current);
                String []currentDateSplit = currentDate.split(" ");
                String firstTerm = currentDateSplit[0] + " 08-00";
                Date firstTermDate = sp.parse(firstTerm);
                System.out.println(firstTermDate);
                Date firstDate = listOfDates.get(0);
                Date lastDate = listOfDates.get(listOfDates.size() - 1);
                boolean noFree = true;
                while (firstTermDate.before(lastDate)) {
                    String dateFirst = sp.format(firstTermDate);
                    String []split = dateFirst.split(" ");
                    System.out.println(split[1]);
                    if(termini.contains(split[1])){
                        if (listOfDates.contains(firstTermDate)) {
                        } else {
                            r.setFirstAvailableDate(sp.format(firstTermDate));
                            noFree = false;
                            break;
                        }
                    }
                    firstTermDate = new Date(firstTermDate.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                }
                if(noFree){
                    lastDate = new Date(lastDate.getTime() + (1440 * ONE_MINUTE_IN_MILLIS));
                    String last = sp.format(lastDate);
                    String[] pslit = last.split(" ");
                    String newLastDate = pslit[0] + " 10-00";
                    r.setFirstAvailableDate(newLastDate);
                    System.out.println("Prvi slobodan: ");
                    System.out.println(r.getFirstAvailableDate());
                }
            }
        }
        Collection<Room> listaSoba = c.getRooms();
        for (Room r : listaSoba) {
            if (r.getCalendar().isEmpty()) {
                r.setFirstAvailableDate(date + " 10-00");
            }
        }
        ArrayList<Room> freeRoomsForDate = new ArrayList<>();
        for (Room r : listaSoba) {
            if (r.getCalendar().isEmpty()) {
                freeRoomsForDate.add(r);
            } else {
                ArrayList<String> busy = new ArrayList<String>();
                boolean flag = false;
                for (String s : r.getCalendar()) {
                    String[] splited = s.split(" ");
                    if (splited[0].equalsIgnoreCase(date)) {
                        flag = true;
                        busy.add(splited[1]);
                    }
                }

                if(busy.size() != termini.size()){
                    freeRoomsForDate.add(r);
                }else{
                    System.out.println("Isti");
                }



            }
        }

        if (freeRoomsForDate.size() == 0){
            return new ResponseEntity<Collection<Room>>(listaSoba, HttpStatus.OK);

        }
        return new ResponseEntity<Collection<Room>>(freeRoomsForDate, HttpStatus.OK);

    }

    @PostMapping(value = "patient/create-appointment-request/{dateAndTime}/{type}/{patientID}/{doctorID}/{clinicID}")
    public ResponseEntity<String> createAppointmentRequest(@PathVariable String dateAndTime,@PathVariable String type, @PathVariable long clinicID, @PathVariable long patientID, @PathVariable long doctorID ){

        AppointmentRequest ar = new AppointmentRequest(dateAndTime,type,clinicID,patientID,doctorID);
        appointmentRequestRepository.save(ar);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}