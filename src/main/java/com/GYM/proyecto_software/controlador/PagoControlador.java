package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.dto.PagoRequerido;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Pago;
import com.GYM.proyecto_software.patrones.strategy.PagoStrategy;
import com.GYM.proyecto_software.patrones.strategy.PagoStrategyFactory;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoControlador {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PagoStrategyFactory strategyFactory;

    @PostMapping
    @Transactional
    public Pago registrarPago(@RequestBody PagoRequerido request) {

        // 1. Validar Cliente
        Cliente cliente = clienteRepositorio.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // 2. Crear objeto Pago base
        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setMonto(request.getMonto());
        pago.setConcepto(request.getConcepto());
        pago.setFecha(LocalDate.now());


        PagoStrategy estrategia = strategyFactory.obtenerEstrategia(request.getConcepto());

        estrategia.procesarPago(pago, cliente, request);

        return pagoRepositorio.save(pago);
    }
}