package com.shawnrucker.demo;

import com.shawnrucker.demo.models.Role;
import com.shawnrucker.demo.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {

		// Verify that the database contains the necessary roles for the application
		// to operate

		return args -> {
			// Administrator Role
			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}

			// User Manager Role
			Role userManager = roleRepository.findByRole("USERMANAGER");
			if (userManager == null) {
				Role newUserManagerRole = new Role();
				newUserManagerRole.setRole("USERMANAGER");
				roleRepository.save(newUserManagerRole);
			}

			// User Role
			Role user = roleRepository.findByRole("USER");
			if (user == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("USER");
				roleRepository.save(newUserRole);
			}
		};

	}

}
