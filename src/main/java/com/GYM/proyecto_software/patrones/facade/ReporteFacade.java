package com.GYM.proyecto_software.patrones.facade;

import com.GYM.proyecto_software.modelo.Asistencia;
import com.GYM.proyecto_software.modelo.Pago;
import com.GYM.proyecto_software.repositorio.AsistenciaRepositorio;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.PagoRepositorio;
import com.GYM.proyecto_software.repositorio.UsoMaquinaExtraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteFacade {

    @Autowired private ClienteRepositorio clienteRepo;
    @Autowired private PagoRepositorio pagoRepo;
    @Autowired private AsistenciaRepositorio asistenciaRepo;
    @Autowired private UsoMaquinaExtraRepositorio usoExtraRepo;

    public Map<String, Object> obtenerReporteGeneral() {
        Map<String, Object> reporte = new HashMap<>();

        // 1. Contadores Básicos
        reporte.put("total_clientes", clienteRepo.count());
        reporte.put("clientes_entrenando_ahora", asistenciaRepo.countByEstado("DENTRO"));
        reporte.put("total_transacciones", pagoRepo.count());

        // 2. Finanzas (Suma total de ingresos)
        List<Pago> todosLosPagos = pagoRepo.findAll();
        BigDecimal totalIngresos = todosLosPagos.stream()
                .map(Pago::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        reporte.put("ingresos_totales", totalIngresos);

        // 3. Uso de Máquinas Restringidas
        reporte.put("total_usos_extra", usoExtraRepo.count());

        // 4. Cálculo de Hora Pico (Basado en entradas históricas)
        // Agrupamos todas las asistencias por hora del día (0-23)
        List<Asistencia> historial = asistenciaRepo.findAll();

        Map<Integer, Long> frecuenciaPorHora = historial.stream()
                .map(a -> a.getEntrada().getHour())
                .collect(Collectors.groupingBy(h -> h, Collectors.counting()));

        // Encontrar la hora con más afluencia
        String horaPico = frecuenciaPorHora.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + ":00 - " + (entry.getKey()+1) + ":00")
                .orElse("Sin datos suficientes");

        reporte.put("hora_pico_historica", horaPico);

        return reporte;
    }
}