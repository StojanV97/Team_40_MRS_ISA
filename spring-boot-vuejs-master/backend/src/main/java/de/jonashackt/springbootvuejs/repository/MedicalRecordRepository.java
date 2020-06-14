package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

    MedicalRecord findByMedicalRecordId(@Param("medicalRecordId") long medicalRecordId);

    MedicalRecord findByPatientId(@Param("patientId") long patientId);
}
