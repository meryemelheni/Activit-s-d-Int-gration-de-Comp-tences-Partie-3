package com.example.grading.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "etudiant-service")
public interface EtudiantServiceClient {
    @GetMapping("/api/etudiants/{id}")
    Object getEtudiantById(@PathVariable("id") Long id);
}
