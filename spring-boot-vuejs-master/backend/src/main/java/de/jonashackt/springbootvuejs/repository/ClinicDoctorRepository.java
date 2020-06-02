package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.joinedtables.Clinic_Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClinicDoctorRepository extends CrudRepository<Clinic_Doctors,Long> {

}
