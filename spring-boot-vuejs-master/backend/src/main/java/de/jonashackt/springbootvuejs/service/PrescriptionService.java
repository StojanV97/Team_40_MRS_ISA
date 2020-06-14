package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Prescription;

import java.util.Collection;

public interface PrescriptionService {
    Prescription getPrescription(long id);
    Collection<Prescription> getAllPrescriptions();
}
