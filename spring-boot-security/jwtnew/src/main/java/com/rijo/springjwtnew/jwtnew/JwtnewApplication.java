package com.rijo.springjwtnew.jwtnew;

import com.rijo.springjwtnew.jwtnew.entities.Role;
import com.rijo.springjwtnew.jwtnew.entities.User;
import com.rijo.springjwtnew.jwtnew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtnewApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(JwtnewApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if (adminAccount == null) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setLastname("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(passwordEncoder.encode("admin"));
			userRepository.save(user);
		}
	}
}
