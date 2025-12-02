package com.GYM.proyecto_software.patrones.observer;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CambioOcupacionEvento extends ApplicationEvent {
    private final String tipoMovimiento; // "ENTRADA" o "SALIDA"
    private final int ocupacionActual;

    public CambioOcupacionEvento(Object source, String tipoMovimiento, int ocupacionActual) {
        super(source);
        this.tipoMovimiento = tipoMovimiento;
        this.ocupacionActual = ocupacionActual;
    }
}