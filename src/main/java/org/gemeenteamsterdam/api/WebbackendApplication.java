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

import java.io.*;

@SpringBootApplication
public class WebbackendApplication {
	public static void main(String[] args) throws IOException {

		final File configFile = new File("config/application.properties");

		if (!configFile.exists()) generateConfigFile(configFile);

		SpringApplication.run(WebbackendApplication.class, args);
	}

	private static void generateConfigFile(final File file) throws IOException {
		file.getParentFile().mkdirs();

		try (
				InputStream inputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("application.properties");
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				OutputStream outputStream = new FileOutputStream(file)
		) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}


	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Profile({"local", "dev", "prod", "default"})
	CommandLineRunner initializeValuesForDatabase(UserServiceImpl userService, ApplicationConfig applicationConfig) {
		return args -> {
			userService.createUser(new CreateAccountDTO(applicationConfig.getAdminEmail(), applicationConfig.getAdminPassword(), "admin"));
			userService.addRoleToUser(applicationConfig.getAdminEmail(), "ROLE_ADMIN");
		};
	}
}
