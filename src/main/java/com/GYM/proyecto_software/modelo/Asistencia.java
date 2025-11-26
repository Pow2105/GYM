package com.GYM.proyecto_software.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
@Data
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsistencia;

    @Column(nullable = false)
    private LocalDateTime entrada;

    private LocalDateTime salida;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}