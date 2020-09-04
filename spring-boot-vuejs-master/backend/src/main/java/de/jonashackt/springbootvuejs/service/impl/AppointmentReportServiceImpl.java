package de.jonashackt.springbootvuejs.service.impl;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.repository.AppointmentReportRepository;
import de.jonashackt.springbootvuejs.repository.AppointmentRequestRepository;
import de.jonashackt.springbootvuejs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class AppointmentReportServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentReportRepository appointmentReportRepository;

    @Override
    public Collection<Appointment> getAllAppointments() {
        return null;
    }
}
