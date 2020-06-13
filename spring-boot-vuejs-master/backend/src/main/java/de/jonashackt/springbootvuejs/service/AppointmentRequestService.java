package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.AppointmentRequest;

import java.util.Collection;

public interface AppointmentRequestService {
    String createAppointmentRequest(AppointmentRequest request);

    Collection<AppointmentRequest> getAllRequests();

    String delete(long appointmentID);
}
