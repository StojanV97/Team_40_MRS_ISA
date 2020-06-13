package de.jonashackt.springbootvuejs.service.impl;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import de.jonashackt.springbootvuejs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Collection<Appointment> getAllAppointments() {
        return (Collection<Appointment>) appointmentRepository.findAll() ;

    }
}
