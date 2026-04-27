package com.example.etudiants.service;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.exception.ResourceNotFoundException;
import com.example.etudiants.mapper.DepartementMapper;
import com.example.etudiants.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartementService {

    private final DepartementRepository repository;
    private final DepartementMapper mapper;

    @Cacheable(value = "departements")
    public List<DepartementDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "departements", key = "#id")
    public DepartementDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'id: " + id));
    }

    @CacheEvict(value = "departements", allEntries = true)
    public DepartementDTO save(DepartementDTO dto) {
        Departement entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @CacheEvict(value = "departements", allEntries = true)
    public DepartementDTO update(Long id, DepartementDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Département non trouvé avec l'id: " + id);
        }
        Departement entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    @CacheEvict(value = "departements", allEntries = true)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Département non trouvé avec l'id: " + id);
        }
        repository.deleteById(id);
    }
}
