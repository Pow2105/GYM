package com.GYM.proyecto_software.patrones.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PagoStrategyFactory {

    @Autowired
    private Map<String, PagoStrategy> estrategias;

    public PagoStrategy obtenerEstrategia(String concepto) {
        // Convertimos el concepto (ej: "MENSUAL") al nombre del Bean ("ESTRATEGIA_MENSUAL")
        String nombreEstrategia = "ESTRATEGIA_" + concepto.toUpperCase();

        PagoStrategy estrategia = estrategias.get(nombreEstrategia);

        if (estrategia == null) {

            return estrategias.get("ESTRATEGIA_DIARIO");
        }

        return estrategia;
    }
}