package com.GYM.proyecto_software.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "administradores")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdmin;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;

    private String rol;
}