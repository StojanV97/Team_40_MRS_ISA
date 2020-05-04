package de.jonashackt.springbootvuejs.service;


import de.jonashackt.springbootvuejs.domain.Clinic;

public interface ClinicService {
    String createClinic(Clinic clinic);
    String deleteClinic(long id);
    String createClinicAgain(Clinic clinic);
}
