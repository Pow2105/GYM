package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Asistencia;
import com.GYM.proyecto_software.servicio.AsistenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaControlador {

    @Autowired
    private AsistenciaServicio asistenciaServicio;

    @PostMapping("/escanear")
    public ResponseEntity<?> escanearQr(@RequestParam String qrCode) {
        try {
            Asistencia asistencia = asistenciaServicio.registrarAcceso(qrCode);
            return ResponseEntity.ok(asistencia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}