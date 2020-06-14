package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Prescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {
    Prescription findByPrescriptionId(@Param("prescriptionId") long prescriptionId);
}
