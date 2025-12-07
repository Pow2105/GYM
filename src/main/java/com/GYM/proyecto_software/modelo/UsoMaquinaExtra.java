package com.GYM.proyecto_software.modelo;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "uso_maquina_extra")
public class UsoMaquinaExtra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUso;
    private LocalDate fecha;
    @Column(name = "costo_extra") private BigDecimal costoExtra;

    @ManyToOne @JoinColumn(name = "id_cliente") private Cliente cliente;
    @ManyToOne @JoinColumn(name = "id_maquina") private Maquina maquina;
}