package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.MedicalRecord;

public interface MedicalRecordService {
    MedicalRecord getMedicalRecord(long id);
}
