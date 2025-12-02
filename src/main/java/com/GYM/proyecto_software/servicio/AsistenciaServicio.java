package com.GYM.proyecto_software.servicio;

import com.GYM.proyecto_software.modelo.Asistencia;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.patrones.observer.CambioOcupacionEvento;
import com.GYM.proyecto_software.repositorio.AsistenciaRepositorio;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AsistenciaServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    private ApplicationEventPublisher eventPublisher; // Para publicar eventos (Observer)

    public Asistencia registrarAcceso(String qrCode) {
        // 1. Buscar al cliente
        Cliente cliente = clienteRepositorio.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ese código QR"));

        // 2. Lógica de Entrada/Salida
        Optional<Asistencia> ultimaAsistenciaOpt = asistenciaRepositorio.findTopByClienteOrderByEntradaDesc(cliente);
        Asistencia asistenciaGuardada;
        String movimiento;

        if (ultimaAsistenciaOpt.isPresent() && ultimaAsistenciaOpt.get().getSalida() == null) {
            // Es SALIDA
            Asistencia ultima = ultimaAsistenciaOpt.get();
            ultima.setSalida(LocalDateTime.now());
            ultima.setEstado("FUERA");
            asistenciaGuardada = asistenciaRepositorio.save(ultima);
            movimiento = "SALIDA";
        } else {
            // Es ENTRADA
            Asistencia nueva = new Asistencia();
            nueva.setCliente(cliente);
            nueva.setEntrada(LocalDateTime.now());
            nueva.setEstado("DENTRO");
            asistenciaGuardada = asistenciaRepositorio.save(nueva);
            movimiento = "ENTRADA";
        }

        // 3. PATRÓN OBSERVER: Notificar el cambio
        // Calculamos cuántos hay "DENTRO" ahora mismo
        int ocupacionActual = (int) asistenciaRepositorio.countByEstado("DENTRO");

        // Disparamos el evento
        eventPublisher.publishEvent(new CambioOcupacionEvento(this, movimiento, ocupacionActual));

        return asistenciaGuardada;
    }
}