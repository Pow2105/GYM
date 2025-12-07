package com.GYM.proyecto_software.patrones.strategy;

import com.GYM.proyecto_software.dto.PagoRequerido;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Maquina;
import com.GYM.proyecto_software.modelo.Pago;
import com.GYM.proyecto_software.modelo.UsoMaquinaExtra;
import com.GYM.proyecto_software.repositorio.MaquinaRepositorio;
import com.GYM.proyecto_software.repositorio.UsoMaquinaExtraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("ESTRATEGIA_EXTRA")
public class PagoExtraStrategy implements PagoStrategy {

    @Autowired
    private MaquinaRepositorio maquinaRepositorio;

    @Autowired
    private UsoMaquinaExtraRepositorio usoMaquinaExtraRepositorio;

    @Override
    public void procesarPago(Pago pago, Cliente cliente, PagoRequerido request) {
        // 1. Validar que venga el ID de la máquina
        if (request.getIdMaquina() == null) {
            throw new RuntimeException("Para un pago EXTRA debe especificar el idMaquina");
        }

        // 2. Buscar la máquina
        Maquina maquina = maquinaRepositorio.findById(request.getIdMaquina())
                .orElseThrow(() -> new RuntimeException("Máquina no encontrada"));

        // 3. Registrar el uso extra
        UsoMaquinaExtra usoExtra = new UsoMaquinaExtra();
        usoExtra.setCliente(cliente);
        usoExtra.setMaquina(maquina);
        usoExtra.setFecha(LocalDate.now());
        usoExtra.setCostoExtra(request.getMonto());

        usoMaquinaExtraRepositorio.save(usoExtra);
        
    }
}