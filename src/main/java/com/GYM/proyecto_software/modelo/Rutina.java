package com.GYM.proyecto_software.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "rutinas")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutina;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_entrenador", nullable = false)
    private Entrenador entrenador;

    @PrePersist
    public void prePersist() {
        if (this.fechaAsignacion == null) {
            this.fechaAsignacion = LocalDate.now();
        }
    }
}