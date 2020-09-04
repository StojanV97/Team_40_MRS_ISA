package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.domain.AppointmentReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppointmentReportRepository extends CrudRepository<AppointmentReport,Long> {

    AppointmentReport findById(@Param("id") long id);
}
