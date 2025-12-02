package com.GYM.proyecto_software.patrones.strategy;

import com.GYM.proyecto_software.dto.PagoRequerido;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Pago;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ESTRATEGIA_DIARIO")
public class PagoDiarioStrategy implements PagoStrategy {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void procesarPago(Pago pago, Cliente cliente, PagoRequerido request) {

        cliente.setTipoCliente("DIARIO");
        clienteRepositorio.save(cliente);
    }
}