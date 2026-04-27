package com.example.etudiants;

import com.example.etudiants.entity.Etudiant;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class EtudiantStepDefinitions {

    private Etudiant etudiant;
    private int resultatAge;

    @Given("un étudiant avec la date de naissance {string}")
    public void un_etudiant_avec_la_date_de_naissance(String dateNaissance) {
        etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.parse(dateNaissance));
    }

    @When("on calcule son âge")
    public void on_calcule_son_age() {
        resultatAge = etudiant.age();
    }

    @Then("l'âge retourné doit être {int}")
    public void l_age_retourne_doit_etre(Integer attendu) {
        assertEquals(attendu, resultatAge);
    }
}
