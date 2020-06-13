package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.domain.Clinic;

import java.util.Collection;

public interface AppointmentService {

    Collection<Appointment> getAllAppointments();
}
