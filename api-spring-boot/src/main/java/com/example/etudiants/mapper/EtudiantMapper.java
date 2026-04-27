package com.example.etudiants.mapper;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {DepartementMapper.class})
public interface EtudiantMapper {
    EtudiantMapper INSTANCE = Mappers.getMapper(EtudiantMapper.class);

    @Mapping(target = "age", expression = "java(etudiant.age())")
    EtudiantDTO toDTO(Etudiant etudiant);
    
    Etudiant toEntity(EtudiantDTO etudiantDTO);
}
