package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.joinedtables.Clinic_Admin;
import de.jonashackt.springbootvuejs.joinedtables.Clinic_Doctors;
import org.springframework.data.repository.CrudRepository;

public interface ClinicAdminRepository extends CrudRepository<Clinic_Admin,Long> {

}
