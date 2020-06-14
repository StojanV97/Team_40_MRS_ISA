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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	CommandLineRunner runner(DaysOffRepository daysOffRepository,MedicineRepository medicineRepository,AppointmentRepository appointmentRepository ,AppointmentRequestRepository appointmentRequestRepository,ClinicRoomRepository clinicRoomRepository,ClinicDoctorRepository clinicDoctorRepository,UserRepository userRepository, RoomRepository roomRepository, RequestRepository requestRepository, DiagnoseRepository diagnoseRepository ,ClinicRepository clinicRepository){
		return args -> {

			//Date
			Date d = new Date();
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			ArrayList<String> listOfDates = new ArrayList<String>();
			String d2 = sp.format(d);
			listOfDates.add("2020-06-15 10-00");
			listOfDates.add("2020-06-15 10-30");
			listOfDates.add("2020-06-15 11-00");
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
			Room room = new Room(1,"Examination");
			Room room2 = new Room(2,"Examination");
			Room room3 = new Room(3,"Operation");
			Room room4 = new Room(4,"Examination");
			Room room5 = new Room(5,"Operation");
			room5.setCalendar(listOfDates);
			Clinic clinic = new Clinic(1, "Klinika BEOGRAD", "Njegoseva 12");
			Clinic clinic2 = new Clinic(2, "Klinicki Centar Novi sad", "Bulevar Oslobodjenja 12");
			room.setCalendar(listOfDates);
			roomRepository.save(room);
			roomRepository.save(room2);
			roomRepository.save(room3);
			roomRepository.save(room4);
			roomRepository.save(room5);
			clinicRepository.save(clinic);
			clinicRepository.save(clinic2);
			//===============================================================================================================================

			//Users
			ClinicCenterAdmin clinincCenterAdmin = new ClinicCenterAdmin("ClinicCenterAdmin","ClinicCenterAdmin","ClinicCenterAdmin@gmail.com","ClinicCenterAdmin",bc.encode("password"));
			ClinicAdmin clinincAdmin = new ClinicAdmin("ClinicAdmin","ClinicAdmin","ClinicAdmin@gmail.com","ClinicAdmin",bc.encode("password"));
			ArrayList<Long> appointments = new ArrayList<>();
			Patient patient = new Patient("Milan","Gajic","mikig@gmail.com","Patient",bc.encode("123"),"Srbija", "Novi Sad", "Bulevar 154", "016123423", "12312312312",appointments);
			//Patient patient = new Patient("Milan","Gajic","mikig@gmail.com","Patient",bc.encode("123"),"Srbija", "Novi Sad", "Bulevar 154", "016123423", "12312312312");
			patient.setPassChanged(true);
			Patient patient2 = new Patient("Pera","Peric","mikig@gmail.com","Patient2",bc.encode("123"),"Srbija", "Novi Sad", "Bulevar 154", "016123423", "12312312312");
			Doctor doctor = new Doctor("Doctor","Doctor","Doctor@gmail.com","Doctor",bc.encode("password"));
			Doctor doctor2 = new Doctor("Doctor2","Doctor2","Doctor2@gmail.com","Doctor2",bc.encode("password"));
			Doctor doctor22 = new Doctor("Doctor2","Doctor2","Doctor2@gmail.com","Doctor22",bc.encode("password"));
			Doctor doctor23 = new Doctor("Doctor2","Doctor2","Doctor2@gmail.com","Doctor23",bc.encode("password"));
			Doctor doctor3 = new Doctor("Doctor3","Doctor3","Doctor3@gmail.com","Doctor3",bc.encode("password"));
			Doctor doctor4 = new Doctor("Doctor4","Doctor4","Doctor4@gmail.com","Doctor4",bc.encode("password"));
			Nurse nurse = new Nurse("Nurse","Nurse","Nurse@gmail.com","Nurse",bc.encode("password"));
			clinincCenterAdmin.getAuthorities().add(clinicCenterAdminAuthority);
			clinincAdmin.getAuthorities().add(clinicAdminAuthority);
			clinincAdmin.setPassChanged(true);
			nurse.getAuthorities().add(nurseAuthority);
			patient.getAuthorities().add(patientAuthority);
			doctor.setListOfPatients((long) 1);
			patient2.getAuthorities().add(patientAuthority);
			doctor.getAuthorities().add(doctorAuthority);
			doctor2.getAuthorities().add(doctorAuthority);
			doctor3.getAuthorities().add(doctorAuthority);
			doctor4.getAuthorities().add(doctorAuthority);
			doctor.setListOfAppoitnements("2020-06-14 13-30");
			doctor.setListOfAppoitnements("2020-07-20 10-30");
			doctor.setListOfAppoitnements("2020-07-13 11-00");
			doctor.setListOfAppoitnements("2020-08-16 11-30");
			doctor.setPassChanged(true);
			clinincAdmin.setClinicName((long) 1);
			DaysOffRequest daysOffRequest = new DaysOffRequest((long) 3,"Doctor","Doctor","2020-06-14","2020-07-00","Vacation",2,"stojan.v1997@gmail.com");
			//daysOffRepository.save(daysOffRequest); Nmg da sacuvam request, moze se kreairati u DoctorHomePage -> Request Days off
			userRepository.save(patient);
			userRepository.save(patient2);
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
			Date date = new Date();
			appointmentRequestRepository.save(new AppointmentRequest("2020-06-15","EXAMINATION", 1,2,3));
			requestRepository.save(new RegisterRequests("Milan","Milanovic","stojan.v19937@gmail.com","milan", "Srbija", "Novi Sad", "Bulevar 2", "061144111", "12388891132"));
			requestRepository.save(new RegisterRequests("Dragan","Dragunovic","stojan.v19297@gmail.com","dragan", "Srbija", "Novi Sad", "Bulevar 3", "061132111", "1234230678"));
			requestRepository.save(new RegisterRequests("Stojan","Stojanovic","stojan.v19497@gmail.com","stojan", "Srbija", "Novi Sad", "Bulevar 4", "0611565111", "1234881132"));

			appointmentRequestRepository.save(new AppointmentRequest("2020-07-07","EXAMINATION", 1,1,3));

			Appointment apt= new Appointment("2020-06-14 13-30", "EXAMINATION",1,1,3,2);
			appointmentRepository.save(apt);
			appointments.add(apt.getId());
			Appointment apt2 =new Appointment("2020-07-20 10-30", "OPERATION",1,1,3,2);
			appointmentRepository.save(apt2);
			Appointment apt3 =new Appointment("2020-07-13 11-00", "EXAMINATION",1,1,3,2);
			appointmentRepository.save(apt3);
			Appointment apt4 =new Appointment("2020-08-16 11-30", "EXAMINATION",1,1,3,2);

			appointmentRepository.save(apt4);

			appointments.add(apt2.getId());

			appointments.add(apt3.getId());
			appointments.add(apt4.getId());



			patient.setAppointments(appointments);
			userRepository.save(patient);
			//===========================================================================

            Clinic_Doctors cd = new Clinic_Doctors(clinic.getId(),doctor.getId());
			Clinic_Doctors cd2 = new Clinic_Doctors(clinic.getId(),doctor2.getId());
			Clinic_Doctors cd3 = new Clinic_Doctors(clinic2.getId(),doctor3.getId());
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
			ClinicRooms clinicRoom2 = new ClinicRooms(clinic.getId(),room2.getRoomID());
			ClinicRooms clinicRoom3 = new ClinicRooms(clinic.getId(),room3.getRoomID());
			ClinicRooms clinicRoom4 = new ClinicRooms(clinic.getId(),room4.getRoomID());
			ClinicRooms clinicRoom5 = new ClinicRooms(clinic.getId(),room5.getRoomID());
			clinicRoomRepository.save(clinicRoom);
			clinicRoomRepository.save(clinicRoom2);
			clinicRoomRepository.save(clinicRoom3);
			clinicRoomRepository.save(clinicRoom4);
			clinicRoomRepository.save(clinicRoom5);

			requestRepository.save(new RegisterRequests("p1","p1","djordjeognjenovic359@gmail.com","p1"));
			requestRepository.save(new RegisterRequests("p2","p2","djordjeognjenovic359@gmail.com","p2"));

			medicineRepository.save(new Medicine(1, "lek1", "valja"));
			diagnoseRepository.save(new Diagnose(1, "dijagnoza", "nista strasno"));

		};

	}
}
