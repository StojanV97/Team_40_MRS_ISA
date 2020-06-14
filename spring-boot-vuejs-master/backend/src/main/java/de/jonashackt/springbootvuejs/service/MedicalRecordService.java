package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.MedicalRecord;

public interface MedicalRecordService {
    MedicalRecord getMedicalRecord(long id);
    String createMedicalRecord(MedicalRecord medicalRecord);
    String deleteMedicalRecord(long id);
}
