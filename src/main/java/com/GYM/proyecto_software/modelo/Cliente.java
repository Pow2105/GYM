package com.GYM.proyecto_software.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefono;

    @Column(name = "tipo_cliente", nullable = false)
    private String tipoCliente;

    @Column(name = "qr_code", unique = true)
    private String qrCode;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Suscripcion> suscripciones;
}