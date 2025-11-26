package com.GYM.proyecto_software.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoRequerido {
    private Long idCliente;
    private BigDecimal monto;
    private String concepto;
}