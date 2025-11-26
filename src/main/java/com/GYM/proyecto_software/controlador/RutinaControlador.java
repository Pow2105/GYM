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

    // Asignar una nueva rutina
    @PostMapping
    public Rutina asignarRutina(@RequestBody Rutina rutina) {
        return rutinaRepositorio.save(rutina);
    }

    // Ver rutinas de un cliente específico
    @GetMapping("/cliente/{idCliente}")
    public List<Rutina> getRutinasPorCliente(@PathVariable Long idCliente) {
        return rutinaRepositorio.findByCliente_IdCliente(idCliente);
    }
}