package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.patrones.facade.ReporteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteControlador {

    @Autowired
    private ReporteFacade reporteFacade;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> verDashboard() {
        // El controlador no sabe de dónde salen los datos, solo llama a la fachada
        return ResponseEntity.ok(reporteFacade.obtenerReporteGeneral());
    }
}