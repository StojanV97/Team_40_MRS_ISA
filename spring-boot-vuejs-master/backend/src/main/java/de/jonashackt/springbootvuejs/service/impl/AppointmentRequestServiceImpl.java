package de.jonashackt.springbootvuejs.service.impl;


import de.jonashackt.springbootvuejs.domain.AppointmentRequest;
import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import de.jonashackt.springbootvuejs.repository.AppointmentRequestRepository;
import de.jonashackt.springbootvuejs.service.AppointmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class AppointmentRequestServiceImpl implements AppointmentRequestService {

    @Autowired
    private AppointmentRequestRepository appointmentRequestRepository;

    @Override
    public String createAppointmentRequest(AppointmentRequest request) {

        //808 - korisnik sa tim korisnickim imenom vec postoji
        // 800 - zahtev za registraciju upisan
        // 801 - zaobisao uslove
        String response = "801";


        appointmentRequestRepository.save(new AppointmentRequest(request.getDateAndTime(), request.getType(),
                request.getClinicID(),request.getPatientID(), request.getDoctorID()));
        response = "800";

        return response;
    }

    @Override
    public Collection<AppointmentRequest> getAllRequests() {
        return (Collection<AppointmentRequest>) appointmentRequestRepository.findAll();
    }

    @Override
    public String delete(long appointmentID) {
        System.out.println(appointmentID);
        AppointmentRequest ar = appointmentRequestRepository.findById(appointmentID);
        appointmentRequestRepository.delete(ar);
        return "Deleted";
    }
}
