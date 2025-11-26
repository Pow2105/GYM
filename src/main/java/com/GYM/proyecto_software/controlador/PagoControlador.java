package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.dto.PagoRequerido;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Pago;
import com.GYM.proyecto_software.modelo.Suscripcion;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.PagoRepositorio;
import com.GYM.proyecto_software.repositorio.SuscripcionRepositorio;
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
    private SuscripcionRepositorio suscripcionRepositorio;

    @PostMapping
    @Transactional

    public Pago registrarPago(@RequestBody PagoRequerido request) {


        Cliente cliente = clienteRepositorio.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));


        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setMonto(request.getMonto());
        pago.setConcepto(request.getConcepto());
        pago.setFecha(LocalDate.now());


        if ("MENSUAL".equalsIgnoreCase(request.getConcepto())) {
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

        return pagoRepositorio.save(pago);
    }
}