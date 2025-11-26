package com.GYM.proyecto_software.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "entrenadores")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrenador;

    private String nombre;
    private String especialidad;
    private String disponibilidad;
}