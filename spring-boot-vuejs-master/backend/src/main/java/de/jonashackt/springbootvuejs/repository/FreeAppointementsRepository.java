package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.FreeAppointements;
import de.jonashackt.springbootvuejs.domain.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FreeAppointementsRepository extends CrudRepository<FreeAppointements,Long> {


}
