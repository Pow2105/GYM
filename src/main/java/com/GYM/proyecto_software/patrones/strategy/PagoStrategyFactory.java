package com.GYM.proyecto_software.patrones.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class PagoStrategyFactory {

    // Spring inyecta automáticamente todos los componentes que implementen PagoStrategy en este mapa
    @Autowired
    private Map<String, PagoStrategy> estrategias;

    public PagoStrategy obtenerEstrategia(String concepto) {
        // Mapeamos el concepto del JSON al nombre del componente
        String nombreComponente = "ESTRATEGIA_" + concepto.toUpperCase();

        if (estrategias.containsKey(nombreComponente)) {
            return estrategias.get(nombreComponente);
        }
        // Retornar una estrategia por defecto o lanzar error
        throw new IllegalArgumentException("Tipo de pago no soportado: " + concepto);
    }
}