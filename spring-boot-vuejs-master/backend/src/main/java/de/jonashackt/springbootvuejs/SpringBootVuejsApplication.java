package de.jonashackt.springbootvuejs;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.joinedtables.ClinicRooms;
import de.jonashackt.springbootvuejs.joinedtables.Clinic_Admin;
import de.jonashackt.springbootvuejs.joinedtables.Clinic_Doctors;
import de.jonashackt.springbootvuejs.joinedtables.Record_Reports;
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
	CommandLineRunner runner(AppointmentReportRepository appointmentReportRepository, FreeAppointementsRepository freeAppointementsRepository,PriceBookRepository priceBookRepository,MedicalRecordRepository  medicalRecordRepository, DaysOffRepository daysOffRepository, MedicineRepository medicineRepository, AppointmentRepository appointmentRepository , AppointmentRequestRepository appointmentRequestRepository, ClinicRoomRepository clinicRoomRepository, ClinicDoctorRepository clinicDoctorRepository, UserRepository userRepository, RoomRepository roomRepository, RequestRepository requestRepository, DiagnoseRepository diagnoseRepository , ClinicRepository clinicRepository, ClinicAdminRepository clinicAdminRepository, RecordReportsRepository recordReportsRepository){
		return args -> {

			//Date
			Date d = new Date();
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			ArrayList<String> listOfDates = new ArrayList<String>();
			String d2 = sp.format(d);
			listOfDates.add("2020-09-11 10-00");
			listOfDates.add("2020-08-19 10-30");
			listOfDates.add("2020-08-19 11-00");
			priceBookRepository.save(new PriceBook("Examination",50));
			priceBookRepository.save(new PriceBook("Operation",250));

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
			Clinic clinic = new Clinic(1, "Klinika u BEOGRAD", "Njegoseva 12, 11000 Beograd");
			clinic.setDescription("The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through.");
			Clinic clinic2 = new Clinic(2, "Klinicki Centar Novi sad", "Bulevar Oslobodjenja 12, 21000 Novi Sad");
			room.setCalendar(listOfDates);
			roomRepository.save(room);
			roomRepository.save(room2);
			roomRepository.save(room3);
			roomRepository.save(room4);
			roomRepository.save(room5);
			clinicRepository.save(clinic);
			clinicRepository.save(clinic2);
			Clinic clinic3 = new Clinic(3, "Dom zdravlja NS", "Petra Drapsina 54, 21000 Novi Sad");
			clinicRepository.save(clinic3);
			//===============================================================================================================================

			//Users
			ClinicCenterAdmin clinincCenterAdmin = new ClinicCenterAdmin(10,"ClinicCenterAdmin","ClinicCenterAdmin","ClinicCenterAdmin@gmail.com","ClinicCenterAdmin",bc.encode("password"));
			ClinicCenterAdmin predefinedCCA = new ClinicCenterAdmin(100,"ClinicCenterAdmin","ClinicCenterAdmin","ClinicCenterAdmin@gmail.com","predefinedCCA",bc.encode("password"));
			ClinicAdmin clinincAdmin = new ClinicAdmin(11,"ClinicAdmin","ClinicAdmin","ClinicAdmin@gmail.com","ClinicAdmin",bc.encode("password"));
			ClinicAdmin clinincAdmin2 = new ClinicAdmin(22,"ClinicAdmin2","ClinicAdmin","ClinicAdmin@gmail.com","ClinicAdmin2",bc.encode("password"));
			ArrayList<Long> appointments = new ArrayList<>();
			Patient patient = new Patient(1,"Milan","Gajic","mikig@gmail.com","Patient",bc.encode("123"),"Srbija", "Novi Sad", "Bulevar 154", "061233423", "12312312312",appointments);
			//Patient patient3 = new Patient("Zoran","Gajic","mikig@gmail.com","Patient",bc.encode("123"),"Srbija", "Novi Sad", "Bulevar 154", "016123423", "12312312312");
			patient.setPassChanged(true);
			Patient patient2 = new Patient(2,"Pera","Peric","mikisssg@gmail.com","Patient2",bc.encode("123"),"Srbija", "Novi Sad", "Nikole Tesle 154", "06127723", "12312312312");
			Doctor doctor = new Doctor(3,"Novak","Djokevic","Doctor@gmail.com","Doctor",bc.encode("password"));
			Doctor doctor2 = new Doctor(4,"Jovana","Zoric","Doctor2@gmail.com","Doctor2",bc.encode("password"));
			Doctor doctor22 = new Doctor(5,"Mile","Dragic","Doctor3@gmail.com","Doctor22",bc.encode("password"));
			Doctor doctor23 = new Doctor(6,"Mila","Devic","Doctor4@gmail.com","Doctor23",bc.encode("password"));
			Doctor doctor3 = new Doctor(7,"Strahinja","Doktorovic","Doctor5@gmail.com","Doctor3",bc.encode("password"));
			Doctor doctor4 = new Doctor(8,"Djoka","Sekic","Doctor6@gmail.com","Doctor4",bc.encode("password"));


			Nurse nurse = new Nurse(9,"Dragana","Stefanovic","Nurse@gmail.com","Nurse",bc.encode("password"));


			clinincCenterAdmin.getAuthorities().add(clinicCenterAdminAuthority);
			predefinedCCA.getAuthorities().add(clinicCenterAdminAuthority);
			clinincAdmin.getAuthorities().add(clinicAdminAuthority);
			//clinincAdmin2.getAuthorities().add(clinicAdminAuthority);
			clinincAdmin.setPassChanged(true);
			nurse.getAuthorities().add(nurseAuthority);
			patient.getAuthorities().add(patientAuthority);
			doctor.setListOfPatients((long) 1);
			patient2.getAuthorities().add(patientAuthority);
			doctor.getAuthorities().add(doctorAuthority);
			doctor2.getAuthorities().add(doctorAuthority);
			doctor3.getAuthorities().add(doctorAuthority);
			doctor4.getAuthorities().add(doctorAuthority);
			doctor.setListOfAppoitnements("2020-09-04 13-30");
			doctor.setPassChanged(true);
			nurse.setPassChanged(true);
			clinincAdmin.setClinicName((long) 1);
			//DaysOffRequest daysOffRequest = new DaysOffRequest((long) 3,"Doctor","Doctor","2020-06-14","2020-07-00","Vacation",2,"stojan.v1997@gmail.com");
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
			userRepository.save(predefinedCCA);
			userRepository.save(clinincAdmin);
			//userRepository.save(clinincAdmin2);
			userRepository.save(nurse);
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr1"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v196697@gmail.com","rr2"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v11997@gmail.com","rr3"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v134997@gmail.com","rr4"));
			Date date = new Date();
			appointmentRequestRepository.save(new AppointmentRequest("2020-09-07","EXAMINATION", 1,2,3));
			requestRepository.save(new RegisterRequests("Milan","Milanovic","stojan.v19937@gmail.com","milan", "Srbija", "Novi Sad", "Bulevar 2", "061144111", "12388891132"));
			requestRepository.save(new RegisterRequests("Dragan","Dragunovic","stojan.v19297@gmail.com","dragan", "Srbija", "Novi Sad", "Bulevar 3", "061132111", "1234230678"));
			requestRepository.save(new RegisterRequests("Stojan","Stojanovic","noteventryingyo@gmail.com","77777777", "Srbija", "Novi Sad", "Bulevar 4", "0611565111", "1234881132"));

			appointmentRequestRepository.save(new AppointmentRequest("2020-09-07","EXAMINATION", 1,1,3));
			appointmentRequestRepository.save(new AppointmentRequest("2020-09-08","OPERATION", 1,2,3));
			//Appointment apt= new Appointment("2020-08-20 15-30", "EXAMINATION",1,1,3,2);
			Appointment apt= new Appointment("2020-08-22 11-30", "EXAMINATION",2,1,6,1);
			AppointmentReport apr = new AppointmentReport(13l,"Cold","Light cough and a sore throat.");
			appointmentReportRepository.save(apr);

			appointmentRepository.save(apt);

			Appointment apt1= new Appointment("2020-07-12 10-00", "EXAMINATION",4,1,8,2);
			AppointmentReport apr1 = new AppointmentReport(14l,"Migrene","Headache and dizziness");
			appointmentReportRepository.save(apr1);

			appointmentRepository.save(apt1);

			Appointment apt2= new Appointment("2020-05-21 13-00", "OPERATION",3,1,5,1);
			AppointmentReport apr2 = new AppointmentReport(15l,"Lung Surgery","Very dangerous operation but everything was fine !");
			appointmentReportRepository.save(apr2);

			appointmentRepository.save(apt2);



			Appointment apt10= new Appointment("2020-05-21 13-00", "OPERATION",5,2,5,1);
			AppointmentReport apr10 = new AppointmentReport(16l,"Heart Surgery","Very dangerous operation but everything was fine !");
			appointmentReportRepository.save(apr10);

			Appointment apt5= new Appointment("2020-09-20 10-00", "EXAMINATION",2,1,4,1);
			appointmentRepository.save(apt5);

			Appointment apt6= new Appointment("2020-09-20 11-00", "EXAMINATION",4,2,7,2);
			appointmentRepository.save(apt6);
			//appointments.add(apt.getId());
			//Appointment apt2 =new Appointment("2020-08-20 10-30", "OPERATION",1,1,3,2);
			//appointmentRepository.save(apt2);
			//Appointment apt3 =new Appointment("2020-08-13 11-00", "EXAMINATION",1,1,3,2);
			//appointmentRepository.save(apt3);
			//Appointment apt4 =new Appointment("2020-08-16 11-30", "EXAMINATION",1,1,3,2);
			//appointmentRepository.save(apt4);
			//appointments.add(apt2.getId());
			//appointments.add(apt3.getId());
			//appointments.add(apt4.getId());

			//appointmentRequestRepository.save(new AppointmentRequest("2020-06-16","OPERATION", 1,1,3));
			//patient.setAppointments(appointments);
			userRepository.save(patient);


			MedicalRecord m =new MedicalRecord();
			m.setMedicalRecordId(1l);
			m.setBloodType("B");
			m.setHeight(180);
			m.setWeight(80);
			m.setAllergies("Polen");
			m.setPatient(patient);
			medicalRecordRepository.save(m);



			//===========================================================================

            Clinic_Doctors cd = new Clinic_Doctors(clinic.getId(),doctor.getId());
			Clinic_Doctors cd2 = new Clinic_Doctors(clinic.getId(),doctor2.getId());
			Clinic_Doctors cd3 = new Clinic_Doctors(clinic2.getId(),doctor3.getId());
			Clinic_Doctors cd4 = new Clinic_Doctors(clinic2.getId(),doctor4.getId());
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
			ClinicRooms clinicRoom4 = new ClinicRooms(clinic2.getId(),room4.getRoomID());
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

			Clinic_Admin ca = new Clinic_Admin(clinic.getId(), clinincAdmin.getId());
			clinicAdminRepository.save(ca);
			AppointmentReport ar = new AppointmentReport((long) 20);
			Record_Reports rr = new Record_Reports(m.getMedicalRecordId(), ar.getAppointmentReportId());
			recordReportsRepository.save(rr);

			FreeAppointements fa = new FreeAppointements("2020-12-12 10-30", "EXAMINATION",1,3,2);
			freeAppointementsRepository.save(fa);
			FreeAppointements fa1 = new FreeAppointements("2020-10-15 10-30", "EXAMINATION",1,4,1);
			freeAppointementsRepository.save(fa1);
			FreeAppointements fa2 = new FreeAppointements("2020-09-16 10-30", "EXAMINATION",1,5,1);
			FreeAppointements fa4 = new FreeAppointements("2020-09-16 11-30", "EXAMINATION",1,4,2);
			FreeAppointements fa5 = new FreeAppointements("2020-09-16 12-00", "EXAMINATION",1,6,2);
			FreeAppointements fa6 = new FreeAppointements("2020-09-17 10-30", "EXAMINATION",1,7,2);
			freeAppointementsRepository.save(fa2);
			freeAppointementsRepository.save(fa6);
			freeAppointementsRepository.save(fa4);
			freeAppointementsRepository.save(fa5);
		};

	}
}
