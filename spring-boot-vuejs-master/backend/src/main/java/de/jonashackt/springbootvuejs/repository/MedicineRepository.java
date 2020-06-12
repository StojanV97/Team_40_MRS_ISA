package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Medicine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MedicineRepository extends CrudRepository<Medicine, Long> {
    Medicine findByMedicineId(@Param("medicineId") long medicineId);
}
