package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Rutina;
import com.GYM.proyecto_software.repositorio.RutinaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
@CrossOrigin(origins = "*")
public class RutinaControlador {

    @Autowired
    private RutinaRepositorio rutinaRepositorio;

    @PostMapping
    public Rutina asignarRutina(@RequestBody Rutina rutina) {
        return rutinaRepositorio.save(rutina);
    }


    @GetMapping("/cliente/{idCliente}")
    public List<Rutina> getRutinasPorCliente(@PathVariable Long idCliente) {
        return rutinaRepositorio.findByCliente_IdCliente(idCliente);
    }

    @PutMapping("/{id}")
    public Rutina actualizarRutina(@PathVariable Long id, @RequestBody Rutina detallesRutina) {
        return rutinaRepositorio.findById(id)
                .map(rutina -> {
                    rutina.setDescripcion(detallesRutina.getDescripcion());
                    // Podríamos actualizar también el entrenador si cambió
                    if(detallesRutina.getEntrenador() != null) {
                        rutina.setEntrenador(detallesRutina.getEntrenador());
                    }
                    return rutinaRepositorio.save(rutina);
                })
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con id " + id));
    }
}