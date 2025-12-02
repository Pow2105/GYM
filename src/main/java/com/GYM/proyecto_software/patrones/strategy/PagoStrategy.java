package com.GYM.proyecto_software.patrones.strategy;

import com.GYM.proyecto_software.dto.PagoRequerido;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Pago;

public interface PagoStrategy {

    void procesarPago(Pago pago, Cliente cliente, PagoRequerido request);
}