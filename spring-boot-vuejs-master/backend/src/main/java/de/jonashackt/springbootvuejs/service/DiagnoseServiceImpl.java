package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Diagnose;
import de.jonashackt.springbootvuejs.repository.DiagnoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DiagnoseServiceImpl implements DiagnoseService{

    @Autowired
    private DiagnoseRepository diagnoseRepository;

    @Override
    public String createDiagnose(Diagnose diagnose) {
        if(diagnoseRepository.existsById(diagnose.getDiagnoseId())) {
            return "postoji";
        }
        diagnoseRepository.save(diagnose);
        return "upisan";
    }

    @Override
    public String deleteDiagnose(long id) {
        if(diagnoseRepository.existsById(id)) {
            Diagnose d = diagnoseRepository.findByDiagnoseId(id);
            diagnoseRepository.delete(d);
            return "uspesno";
        }
        return "neuspesno";
    }

    @Override
    public Collection<Diagnose> getAllDiasnoses() {
        return (Collection<Diagnose>) diagnoseRepository.findAll();
    }

    @Override
    public Diagnose getDiagnose(long id) {
        if(diagnoseRepository.existsById(id)) {
            return diagnoseRepository.findByDiagnoseId(id);
        }
        return null;
    }
}
