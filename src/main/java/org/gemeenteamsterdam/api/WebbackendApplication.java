package org.gemeenteamsterdam.api;

import org.gemeenteamsterdam.api.dto.CreateAccountDTO;
import org.gemeenteamsterdam.api.entities.Role;
import org.gemeenteamsterdam.api.services.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebbackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebbackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Profile({"local", "dev"})
	CommandLineRunner initializeValuesForLocalDatabase(UserServiceImpl userService) {
		return args -> {
			userService.createUser(new CreateAccountDTO("thomas.berrens@me.com", "pass", "thomasberrens"));

			userService.addRoleToUser("thomas.berrens@me.com", "ROLE_ADMIN");
		};
	}
}
