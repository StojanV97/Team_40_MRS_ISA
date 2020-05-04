package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicServiceImpl implements ClinicService{

    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public String createClinic(Clinic clinic) {
        if(clinicRepository.existsById(clinic.getId())) {
            return "postoji";
        }
        clinicRepository.save(clinic);
        return "upisan";
    }

    @Override
    public String deleteClinic(long id) {
        if(clinicRepository.existsById(id)) {
            Clinic c = clinicRepository.findById(id);
            clinicRepository.delete(c);
            return "uspesno";
        }
        return "neuspesno";
    }

    @Override
    public String createClinicAgain(Clinic clinic) {
        clinicRepository.save(clinic);
        return "upisan";
    }
}
