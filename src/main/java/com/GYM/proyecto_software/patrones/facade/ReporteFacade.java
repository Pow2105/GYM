package com.GYM.proyecto_software.patrones.facade;

import com.GYM.proyecto_software.repositorio.AsistenciaRepositorio;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReporteFacade {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PagoRepositorio pagoRepositorio;

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    // Método único que expone la complejidad de 3 repositorios
    public Map<String, Object> obtenerReporteGeneral() {
        Map<String, Object> reporte = new HashMap<>();

        reporte.put("total_clientes", clienteRepositorio.count());
        reporte.put("total_transacciones", pagoRepositorio.count());
        reporte.put("clientes_entrenando_ahora", asistenciaRepositorio.countByEstado("DENTRO"));

        // Aquí podrías agregar lógica matemática compleja sin ensuciar el controlador

        return reporte;
    }
}