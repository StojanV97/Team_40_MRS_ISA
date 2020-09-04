package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.AppointmentReport;
import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.repository.AppointmentReportRepository;
import de.jonashackt.springbootvuejs.service.AppointmentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentReportController {

    @Autowired
    private AppointmentReportService appointmentReportService;

    @PostMapping(value = "/appointmentReport/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAppointmentReport(@RequestBody AppointmentReport appointmentReport) throws Exception {
        String s = appointmentReportService.createAppointmentReport(appointmentReport);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }
}
