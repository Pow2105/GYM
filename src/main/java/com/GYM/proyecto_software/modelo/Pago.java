package com.GYM.proyecto_software.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private BigDecimal monto;

    private LocalDate fecha;


    private String concepto;


    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;


    @OneToOne
    @JoinColumn(name = "id_suscripcion", nullable = true)
    private Suscripcion suscripcion;
}