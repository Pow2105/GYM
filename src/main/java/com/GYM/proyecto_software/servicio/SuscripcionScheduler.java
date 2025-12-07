package com.GYM.proyecto_software.servicio;

import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Suscripcion;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.SuscripcionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class SuscripcionScheduler {

    @Autowired
    private SuscripcionRepositorio suscripcionRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;


    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void verificarVencimientos() {
        System.out.println("⏰ [AUTO] Verificando suscripciones vencidas...");

        List<Suscripcion> activas = suscripcionRepositorio.findAll(); // Traemos todas para filtrar
        LocalDate hoy = LocalDate.now();
        int vencidas = 0;

        for (Suscripcion sub : activas) {
            // Si está ACTIVA y la fecha de fin es ANTES de hoy (ayer o antes)
            if ("ACTIVA".equals(sub.getEstado()) && sub.getFechaFin().isBefore(hoy)) {

                // 1. Desactivar suscripción
                sub.setEstado("INACTIVA");
                suscripcionRepositorio.save(sub);

                // 2. Cambiar rol del cliente a DIARIO
                Cliente cliente = sub.getCliente();
                if (cliente != null) {
                    cliente.setTipoCliente("DIARIO");
                    clienteRepositorio.save(cliente);
                }
                vencidas++;
            }
        }

        System.out.println("✅ [AUTO] Proceso finalizado. Suscripciones vencidas hoy: " + vencidas);
    }
}