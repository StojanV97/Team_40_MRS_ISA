package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.MedicalRecord;
import de.jonashackt.springbootvuejs.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Override
    public MedicalRecord getMedicalRecord(long id) {
        if(medicalRecordRepository.existsById(id)) {
            return medicalRecordRepository.findByMedicalRecordId(id);
        }
        return null;
    }
}
