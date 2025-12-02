package com.GYM.proyecto_software.patrones.strategy;

import com.GYM.proyecto_software.dto.PagoRequerido;
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
    public void procesarPago(Pago pago, Cliente cliente, PagoRequerido request) {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setCliente(cliente);
        suscripcion.setFechaInicio(LocalDate.now());
        suscripcion.setFechaFin(LocalDate.now().plusDays(30));
        suscripcion.setEstado("ACTIVA");
        suscripcion = suscripcionRepositorio.save(suscripcion);

        pago.setSuscripcion(suscripcion);

        cliente.setTipoCliente("SUSCRIPTOR");
        clienteRepositorio.save(cliente);
    }
}