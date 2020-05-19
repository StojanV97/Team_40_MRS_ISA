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
	CommandLineRunner runner(AuthorityRepository authorityRepository,UserRepository userRepository, RoomRepository roomRepository, RequestRepository requestRepository, ClinicRepository clinicRepository){
		return args -> {
			Authority a = new Authority();
			a.setName(String.valueOf(UserAuthorities.DOCTOR));
			Authority a1 = new Authority();
			a1.setName(String.valueOf(UserAuthorities.SYS_ADMIN));
			authorityRepository.save(a);
			authorityRepository.save(a1);
			Patient p = new Patient("PacijentIme","Prezime","pacijent@gmail.com","Pacijent1","Pacijent1");
			Patient p1 = new Patient("PacijentIme","Prezime","pacijent@gmail.com","Pacijent2","Pacijent2");
			Patient p2 = new Patient("PacijentIme","Prezime","pacijent@gmail.com","Pacijent3","Pacijent3");
			Patient p3 = new Patient("PacijentIme","Prezime","pacijent@gmail.com","Pacijent4","Pacijent4");
			Doctor doc = new Doctor("Korinik1","Korinik1","s@gmail.com","Korinik1","sifra");
			doc.getListOfPatients().add(p.getUsername());
			doc.getListOfPatients().add(p1.getUsername());
			doc.getListOfPatients().add(p2.getUsername());
			doc.getAuthorities().add(a1);
			doc.setPassword(bc.encode("sifra"));
			userRepository.save(p);
			userRepository.save(p1);
			userRepository.save(p2);
			userRepository.save(p3);
			userRepository.save(doc);
			userRepository.save(new Doctor("Korinik2","Korinik2","s@gmail.com","Korinik2","andrija2"));
			userRepository.save(new Nurse("Korinik3","Korinik3","s@gmail.com","Korinik3","andrija3"));
			ArrayList<String> l = new ArrayList<String>();
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			Room r = new Room(14,"Examination");
			String d2 = sp.format(d);
			l.add("1212-12-12");
			l.add("1212-12-14");
			l.add("1212-12-13");
			l.add("1212-12-11");
			r.setCalendar(l);
			roomRepository.save(r);
			roomRepository.save(new Room(2,"Operation"));
			roomRepository.save(new Room(3, "Operation"));
			roomRepository.save(new Room(4,"Operation"));
			roomRepository.save(new Room(5, "Operation"));
			userRepository.save(new ClinicCenterAdmin("CCAime","CCAprezime","cca@gmail.com","CCA1","CCA1"));
			userRepository.save(new ClinicCenterAdmin("CCAime2","CCAprezime2","cca2@gmail.com","CCA2","CCA2"));
			userRepository.save(new ClinicAdmin("CAime","CAprezime","ca@gmail.com","admin",bc.encode("admin")));
			userRepository.save(new ClinicAdmin("CAime2","CAprezime2","ca2@gmail.com","CA2","CA2"));

			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr1"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr2"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr3"));
			requestRepository.save(new RegisterRequests("asdsa","sdqssasd","stojan.v1997@gmail.com","rr4"));
			clinicRepository.save(new Clinic(1, "klinika 1", "adresa 1", "admin1"));
			clinicRepository.save(new Clinic(2, "klinika 2", "adresa 2", "admin2"));


			for(Authority aut : doc.getAuthorities()){
				System.out.println(aut.getName());
			}
		};

	}
}
