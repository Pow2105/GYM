package com.GYM.proyecto_software.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "maquinas")
public class Maquina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaquina;

    private String nombre;

    private String tipo;

    private String estado;
}