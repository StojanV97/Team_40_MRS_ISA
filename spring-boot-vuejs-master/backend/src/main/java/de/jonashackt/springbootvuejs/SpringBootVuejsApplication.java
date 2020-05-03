package de.jonashackt.springbootvuejs;

import de.jonashackt.springbootvuejs.domain.ClinicCenterAdmin;
import de.jonashackt.springbootvuejs.domain.Nurse;
import de.jonashackt.springbootvuejs.domain.Patient;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootVuejsApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootVuejsApplication.class, args);

	}
	@Bean
	CommandLineRunner runner(UserRepository userRepository){
		return args -> {
			userRepository.save(new Nurse("Korinik1","Korinik1","s@gmail.com","Korinik1","Korinik1"));
			userRepository.save(new Nurse("Korinik2","Korinik2","s@gmail.com","Korinik2","andrija2"));
			userRepository.save(new Nurse("Korinik3","Korinik3","s@gmail.com","Korinik3","andrija3"));
			userRepository.save(new Patient("PacijentIme","Prezime","pacijent@gmail.com","Pacijent1","Pacijent1"));
			userRepository.save(new ClinicCenterAdmin("CCAime","CCAprezime","cca@gmail.com","CCA1","CCA1"));
		};

	}
}
