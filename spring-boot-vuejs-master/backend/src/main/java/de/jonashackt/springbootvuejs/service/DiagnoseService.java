package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Diagnose;

import java.util.Collection;

public interface DiagnoseService {
    String createDiagnose(Diagnose diagnose);
    String deleteDiagnose(long id);
    Collection<Diagnose> getAllDiasnoses();
    Diagnose getDiagnose(long id);
}
