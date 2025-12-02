package com.GYM.proyecto_software.patrones.strategy;

import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Pago;
import com.GYM.proyecto_software.modelo.Suscripcion;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.SuscripcionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("ESTRATEGIA_MENSUAL")
public class PagoMensualStrategy implements PagoStrategy {

    @Autowired
    private SuscripcionRepositorio suscripcionRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void procesarPago(Pago pago, Cliente cliente) {
        // 1. Crear Suscripción de 30 días
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setCliente(cliente);
        suscripcion.setFechaInicio(LocalDate.now());
        suscripcion.setFechaFin(LocalDate.now().plusDays(30));
        suscripcion.setEstado("ACTIVA");

        // Guardamos la suscripción
        suscripcion = suscripcionRepositorio.save(suscripcion);

        // 2. Vincular el pago a la suscripción
        pago.setSuscripcion(suscripcion);

        // 3. Actualizar estatus del cliente
        cliente.setTipoCliente("SUSCRIPTOR");
        clienteRepositorio.save(cliente);
    }
}