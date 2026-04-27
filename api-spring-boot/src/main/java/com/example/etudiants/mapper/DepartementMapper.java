package com.example.etudiants.mapper;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartementMapper {
    DepartementMapper INSTANCE = Mappers.getMapper(DepartementMapper.class);

    DepartementDTO toDTO(Departement departement);
    Departement toEntity(DepartementDTO departementDTO);
}
