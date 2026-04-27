package com.example.etudiants.controller;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.service.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departements")
@Tag(name = "Départements", description = "Gestion des départements")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementService service;

    @GetMapping
    @Operation(summary = "Récupérer tous les départements", description = "Retourne la liste complète des départements")
    public List<DepartementDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un département par ID")
    @ApiResponse(responseCode = "200", description = "Département trouvé")
    @ApiResponse(responseCode = "404", description = "Département non trouvé")
    public ResponseEntity<DepartementDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau département")
    @ApiResponse(responseCode = "201", description = "Département créé avec succès")
    public ResponseEntity<DepartementDTO> create(@RequestBody DepartementDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un département")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id, @RequestBody DepartementDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un département")
    @ApiResponse(responseCode = "244", description = "Département supprimé avec succès")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
