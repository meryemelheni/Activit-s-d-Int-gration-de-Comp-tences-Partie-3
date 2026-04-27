package com.example.etudiants.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nom;
}
