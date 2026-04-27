package com.example.etudiants;

import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;

    public DataInitializer(EtudiantRepository etudiantRepository, DepartementRepository departementRepository) {
        this.etudiantRepository = etudiantRepository;
        this.departementRepository = departementRepository;
    }

    @Override
    public void run(String... args) {
        // Nettoyer les données existantes
        etudiantRepository.deleteAll();
        departementRepository.deleteAll();

        // Création des départements
        Departement it = Departement.builder().nom("Informatique").build();
        Departement math = Departement.builder().nom("Mathématiques").build();
        departementRepository.save(it);
        departementRepository.save(math);

        // Création des étudiants
        Etudiant e1 = Etudiant.builder()
                .cin("12345678")
                .nom("Ahmed Ben Salah")
                .email("ahmed@example.com")
                .dateNaissance(LocalDate.of(2002, 4, 7))
                .anneePremiereInscription(2022)
                .departement(it)
                .build();

        Etudiant e2 = Etudiant.builder()
                .cin("87654321")
                .nom("Sonia Mansour")
                .email("sonia@example.com")
                .dateNaissance(LocalDate.of(2003, 10, 15))
                .anneePremiereInscription(2023)
                .departement(math)
                .build();

        etudiantRepository.save(e1);
        etudiantRepository.save(e2);
    }
}
