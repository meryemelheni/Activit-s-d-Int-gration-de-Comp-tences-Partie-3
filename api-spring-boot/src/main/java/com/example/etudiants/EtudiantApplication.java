package com.example.etudiants;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.repository.EtudiantRepository;
import com.example.etudiants.repository.DepartementRepository;
import java.time.LocalDate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@OpenAPIDefinition(
	info = @Info(
		title = "API Gestion des Étudiants",
		version = "1.0.0",
		description = "API REST pour la gestion des étudiants et départements"
	)
)
public class EtudiantApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtudiantApplication.class, args);
	}

	@Bean
	CommandLineRunner start(EtudiantRepository etudiantRepository, DepartementRepository departementRepository) {
		return args -> {
			Departement d1 = departementRepository.save(new Departement(null, "Informatique"));
			Departement d2 = departementRepository.save(new Departement(null, "Mathématiques"));

			etudiantRepository.save(new Etudiant(null, "0972718", "Meryem", LocalDate.of(2000, 8, 4), "merye@gmail.com", 2025, d1));
			etudiantRepository.save(new Etudiant(null, "12345678", "Ahmed Ben Salah", LocalDate.of(2002, 4, 7), "ahmed@example.com", 2022, d1));
			etudiantRepository.save(new Etudiant(null, "87654321", "Sonia Mansour", LocalDate.of(2003, 10, 15), "sonia@example.com", 2023, d2));
			etudiantRepository.save(new Etudiant(null, "11223344", "Yassine Ferchichi", LocalDate.of(2001, 1, 12), "yassine@example.com", 2024, d1));
			etudiantRepository.save(new Etudiant(null, "55667788", "Ines Ben Ammar", LocalDate.of(2002, 6, 20), "ines@example.com", 2023, d2));
		};
	}
}
