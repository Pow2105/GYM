package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Suscripcion;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.SuscripcionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
@CrossOrigin(origins = "*")
public class SuscripcionControlador {

    @Autowired
    private SuscripcionRepositorio suscripcionRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    // Listar todas las suscripciones del sistema
    @GetMapping
    public List<Suscripcion> getAllSuscripciones() {
        return suscripcionRepositorio.findAll();
    }

    // Obtener una suscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<Suscripcion> getSuscripcionById(@PathVariable Long id) {
        return suscripcionRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cancelar manualmente una suscripción (Cambiar estado a INACTIVA)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Suscripcion> cancelarSuscripcion(@PathVariable Long id) {
        return suscripcionRepositorio.findById(id)
                .map(suscripcion -> {
                    suscripcion.setEstado("INACTIVA");
                    return ResponseEntity.ok(suscripcionRepositorio.save(suscripcion));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}