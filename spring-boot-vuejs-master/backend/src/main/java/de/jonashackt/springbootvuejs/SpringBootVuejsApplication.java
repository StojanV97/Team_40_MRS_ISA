package de.jonashackt.springbootvuejs;

import de.jonashackt.springbootvuejs.domain.ClinicCenterAdmin;
import de.jonashackt.springbootvuejs.domain.Nurse;
import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.domain.RoomType;
import de.jonashackt.springbootvuejs.domain.Patient;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.repository.RoomRepository;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class SpringBootVuejsApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootVuejsApplication.class, args);

	}
	@Bean
	CommandLineRunner runner(UserRepository userRepository, RoomRepository roomRepository){
		return args -> {
			userRepository.save(new Nurse("Korinik1","Korinik1","s@gmail.com","Korinik1","Korinik1"));
			userRepository.save(new Nurse("Korinik2","Korinik2","s@gmail.com","Korinik2","andrija2"));
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
			userRepository.save(new Patient("PacijentIme","Prezime","pacijent@gmail.com","Pacijent1","Pacijent1"));
			userRepository.save(new ClinicCenterAdmin("CCAime","CCAprezime","cca@gmail.com","CCA1","CCA1"));
		};

	}
}
