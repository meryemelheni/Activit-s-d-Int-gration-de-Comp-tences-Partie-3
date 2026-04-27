package com.example.etudiants.controller;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@Tag(name = "Étudiants", description = "Gestion des étudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService service;

    @GetMapping
    @Operation(summary = "Récupérer tous les étudiants", description = "Retourne la liste complète des étudiants, filtrable par année ou département")
    public List<EtudiantDTO> getAll(
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Long departementId) {
        if (annee != null) {
            return service.findByAnnee(annee);
        }
        if (departementId != null) {
            return service.findByDepartement(departementId);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un étudiant par ID")
    @ApiResponse(responseCode = "200", description = "Étudiant trouvé")
    @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    public ResponseEntity<EtudiantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel étudiant")
    @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès")
    public ResponseEntity<EtudiantDTO> create(@RequestBody EtudiantDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un étudiant")
    public ResponseEntity<EtudiantDTO> update(@PathVariable Long id, @RequestBody EtudiantDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant")
    @ApiResponse(responseCode = "244", description = "Étudiant supprimé avec succès")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
