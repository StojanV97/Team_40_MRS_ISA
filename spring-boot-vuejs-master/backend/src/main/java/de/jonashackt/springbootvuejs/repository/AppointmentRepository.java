package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment,Long> {

}
