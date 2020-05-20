package de.jonashackt.springbootvuejs;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class SpringBootVuejsApplication {
	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootVuejsApplication.class, args);

	}
	@Bean
	CommandLineRunner runner(UserRepository userRepository, RoomRepository roomRepository, RequestRepository requestRepository, ClinicRepository clinicRepository){
		return args -> {

			//Date
			Date d = new Date();
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			ArrayList<String> listOfDates = new ArrayList<String>();
			String d2 = sp.format(d);
			listOfDates.add("2020-12-12");
			listOfDates.add("2020-12-14");
			listOfDates.add("2020-12-13");
			listOfDates.add("2020-12-11");
			//============================================================================

			//Authorities
			Authority doctorAuthority = new Authority();
			doctorAuthority.setName(String.valueOf(UserAuthorities.DOCTOR));
			Authority sysAdminAuthority = new Authority();
			sysAdminAuthority.setName(String.valueOf(UserAuthorities.SYS_ADMIN));
			Authority nurseAuthority = new Authority();
			nurseAuthority.setName(String.valueOf(UserAuthorities.NURSE));
			Authority patientAuthority = new Authority();
			patientAuthority.setName(String.valueOf(UserAuthorities.PATIENT));
			Authority userAuthority = new Authority();
			patientAuthority.setName(String.valueOf(UserAuthorities.USER));
			Authority clinicAdminAuthority = new Authority();
			patientAuthority.setName(String.valueOf(UserAuthorities.CLINIC_ADMIN));
			Authority clinicCenterAdminAuthority = new Authority();
			patientAuthority.setName(String.valueOf(UserAuthorities.CLINIC_CENTER_ADMIN));
			//============================================================================

			//Users
			ClinicCenterAdmin clinincCenterAdmin = new ClinicCenterAdmin("ClinicCenterAdmin","ClinicCenterAdmin","ClinicCenterAdmin@gmail.com","ClinicCenterAdmin",bc.encode("password"));
			ClinicAdmin clinincAdmin = new ClinicAdmin("ClinicAdmin","ClinicAdmin","ClinicAdmin@gmail.com","ClinicAdmin",bc.encode(bc.encode("password")));
			Patient patient = new Patient("Patient","Patient","Patient@gmail.com","Patient",bc.encode("password"));
			Doctor doctor = new Doctor("Doctor","Doctor","Doctor@gmail.com","Doctor",bc.encode("password"));
			Nurse nurse = new Nurse("Nurse","Nurse","Nurse@gmail.com","Nurse",bc.encode("password"));
			clinincCenterAdmin.getAuthorities().add(clinicCenterAdminAuthority);
			clinincAdmin.getAuthorities().add(clinicAdminAuthority);
			nurse.getAuthorities().add(nurseAuthority);
			patient.getAuthorities().add(userAuthority);
			doctor.getListOfPatients().add(patient.getUsername());
			doctor.getAuthorities().add(doctorAuthority);
			userRepository.save(patient);
			userRepository.save(doctor);
			userRepository.save(clinincCenterAdmin);
			userRepository.save(clinincAdmin);
			userRepository.save(nurse);
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr1"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr2"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr3"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr4"));


			//===========================================================================

			//Utilty
			Room room = new Room(14,"Examination");
			room.setCalendar(listOfDates);
			roomRepository.save(room);
			roomRepository.save(new Room(2,"Operation"));
			roomRepository.save(new Room(3, "Operation"));
			roomRepository.save(new Room(4,"Operation"));
			roomRepository.save(new Room(5, "Operation"));
			clinicRepository.save(new Clinic(1, "klinika 1", "adresa 1", "admin1"));
			clinicRepository.save(new Clinic(2, "klinika 2", "adresa 2", "admin2"));
			//===============================================================================================================================
		};

	}
}
