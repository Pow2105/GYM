package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Asistencia;
import com.GYM.proyecto_software.repositorio.AsistenciaRepositorio;
import com.GYM.proyecto_software.servicio.AsistenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaControlador {

    @Autowired
    private AsistenciaServicio asistenciaServicio;

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @PostMapping("/escanear")
    public ResponseEntity<?> escanearQr(@RequestParam String qrCode) {
        try {
            Asistencia asistencia = asistenciaServicio.registrarAcceso(qrCode);
            return ResponseEntity.ok(asistencia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ocupacion")
    public ResponseEntity<Long> obtenerOcupacion() {
        long cantidadGente = asistenciaRepositorio.countByEstado("DENTRO");
        return ResponseEntity.ok(cantidadGente);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Asistencia>> obtenerHistorialAsistencia(@PathVariable Long idCliente) {
        return ResponseEntity.ok(asistenciaRepositorio.findByCliente_IdCliente(idCliente));
    }
}