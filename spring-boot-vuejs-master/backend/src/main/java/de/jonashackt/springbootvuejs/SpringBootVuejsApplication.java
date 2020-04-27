package de.jonashackt.springbootvuejs;

import de.jonashackt.springbootvuejs.domain.Nurse;
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
			userRepository.save(new Nurse("Andrija","Velickovic","s@gmail.com","adnrija1","andrija1"));
		};

	}
}
