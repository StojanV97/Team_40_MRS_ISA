package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Diagnose;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DiagnoseRepository extends CrudRepository<Diagnose, Long> {
    Diagnose findByDiagnoseId(@Param("diagnoseId") long diagnoseId);
}
