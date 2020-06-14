package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Prescription;
import de.jonashackt.springbootvuejs.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PrescriptionServiceImpl implements PrescriptionService{
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public Prescription getPrescription(long id) {
        if(prescriptionRepository.existsById(id)) {
            prescriptionRepository.findByPrescriptionId(id);
        }
        return null;
    }

    @Override
    public Collection<Prescription> getAllPrescriptions() {
        return (Collection<Prescription>) prescriptionRepository.findAll();
    }
}
