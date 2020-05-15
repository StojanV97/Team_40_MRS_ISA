package de.jonashackt.springbootvuejs.service;


import de.jonashackt.springbootvuejs.domain.Clinic;

import java.util.Collection;

public interface ClinicService {
    String createClinic(Clinic clinic);
    String deleteClinic(long id);
    String createClinicAgain(Clinic clinic);
    Collection<Clinic> getAllClinics();
    Clinic getClinic(long id);
}
