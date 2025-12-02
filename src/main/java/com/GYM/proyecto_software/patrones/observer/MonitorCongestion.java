package com.GYM.proyecto_software.patrones.observer;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MonitorCongestion {

    @EventListener
    public void manejarCambioOcupacion(CambioOcupacionEvento evento) {
        // Aquí simulamos la notificación en tiempo real
        System.out.println(">>> [OBSERVER] Notificación de Congestión <<<");
        System.out.println("Acción: " + evento.getTipoMovimiento());
        System.out.println("Personas en el Gym: " + evento.getOcupacionActual());

        if (evento.getOcupacionActual() >= 50) {
            System.out.println("¡ALERTA! Aforo máximo acercándose.");
        }
        System.out.println("---------------------------------------------");
    }
}