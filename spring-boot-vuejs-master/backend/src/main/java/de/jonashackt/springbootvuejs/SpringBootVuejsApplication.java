package de.jonashackt.springbootvuejs;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.joinedtables.ClinicRooms;
import de.jonashackt.springbootvuejs.joinedtables.Clinic_Doctors;
import de.jonashackt.springbootvuejs.repository.*;
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
	CommandLineRunner runner(ClinicRoomRepository clinicRoomRepository,ClinicDoctorRepository clinicDoctorRepository,UserRepository userRepository, RoomRepository roomRepository, RequestRepository requestRepository, ClinicRepository clinicRepository){
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
			userAuthority.setName(String.valueOf(UserAuthorities.USER));
			Authority clinicAdminAuthority = new Authority();
			clinicAdminAuthority.setName(String.valueOf(UserAuthorities.CLINIC_ADMIN));
			Authority clinicCenterAdminAuthority = new Authority();
			clinicCenterAdminAuthority.setName(String.valueOf(UserAuthorities.CLINIC_CENTER_ADMIN));
			//============================================================================

			//Utilty
			Room room = new Room(14,"Examination");
			Clinic clinic = new Clinic(1, "Klinika", "Njegoseva 12");
			clinic.setAdministrator("ClinicAdmin");
			room.setCalendar(listOfDates);
			roomRepository.save(room);
			roomRepository.save(new Room(2,"Operation"));
			roomRepository.save(new Room(3, "Operation"));
			roomRepository.save(new Room(4,"Operation"));
			roomRepository.save(new Room(5, "Operation"));
			clinicRepository.save(clinic);
			//===============================================================================================================================

			//Users
			ClinicCenterAdmin clinincCenterAdmin = new ClinicCenterAdmin("ClinicCenterAdmin","ClinicCenterAdmin","ClinicCenterAdmin@gmail.com","ClinicCenterAdmin",bc.encode("password"));
			ClinicAdmin clinincAdmin = new ClinicAdmin("ClinicAdmin","ClinicAdmin","ClinicAdmin@gmail.com","ClinicAdmin",bc.encode("password"));
			Patient patient = new Patient("Patient","Patient","Patient@gmail.com","Patient",bc.encode("password"));
			Doctor doctor = new Doctor("Doctor","Doctor","Doctor@gmail.com","Doctor",bc.encode("password"));
			Doctor doctor2 = new Doctor("Doctor2","Doctor2","Doctor2@gmail.com","Doctor2",bc.encode("password"));
			Doctor doctor22 = new Doctor("Doctor2","Doctor2","Doctor2@gmail.com","Doctor22",bc.encode("password"));
			Doctor doctor23 = new Doctor("Doctor2","Doctor2","Doctor2@gmail.com","Doctor23",bc.encode("password"));
			Doctor doctor3 = new Doctor("Doctor3","Doctor3","Doctor3@gmail.com","Doctor3",bc.encode("password"));
			Doctor doctor4 = new Doctor("Doctor4","Doctor4","Doctor4@gmail.com","Doctor4",bc.encode("password"));
			Nurse nurse = new Nurse("Nurse","Nurse","Nurse@gmail.com","Nurse",bc.encode("password"));
			clinincCenterAdmin.getAuthorities().add(clinicCenterAdminAuthority);
			clinincAdmin.getAuthorities().add(clinicAdminAuthority);
			nurse.getAuthorities().add(nurseAuthority);
			patient.getAuthorities().add(patientAuthority);
			doctor.setListOfPatients(patient.getFirstName());
			doctor.getAuthorities().add(doctorAuthority);
			doctor2.getAuthorities().add(doctorAuthority);
			doctor3.getAuthorities().add(doctorAuthority);
			doctor4.getAuthorities().add(doctorAuthority);
			clinincAdmin.setClinicName((long) 1);
			userRepository.save(patient);
			userRepository.save(doctor);
			userRepository.save(doctor2);
			userRepository.save(doctor3);
			userRepository.save(doctor4);
			userRepository.save(doctor22);
			userRepository.save(doctor23);
			userRepository.save(clinincCenterAdmin);
			userRepository.save(clinincAdmin);
			userRepository.save(nurse);
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr1"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr2"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr3"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr4"));

			//===========================================================================

            Clinic_Doctors cd = new Clinic_Doctors(clinic.getId(),doctor.getId());
			Clinic_Doctors cd2 = new Clinic_Doctors(clinic.getId(),doctor2.getId());
			Clinic_Doctors cd3 = new Clinic_Doctors(clinic.getId(),doctor3.getId());
			Clinic_Doctors cd4 = new Clinic_Doctors(clinic.getId(),doctor4.getId());
			Clinic_Doctors cd32 = new Clinic_Doctors(clinic.getId(),doctor22.getId());
			Clinic_Doctors cd43 = new Clinic_Doctors(clinic.getId(),doctor23.getId());
            clinicDoctorRepository.save(cd);
			clinicDoctorRepository.save(cd2);
			clinicDoctorRepository.save(cd3);
			clinicDoctorRepository.save(cd4);
			clinicDoctorRepository.save(cd32);
			clinicDoctorRepository.save(cd43);
			ClinicRooms clinicRoom = new ClinicRooms(clinic.getId(),room.getRoomID());
			clinicRoomRepository.save(clinicRoom);

		};

	}
}
