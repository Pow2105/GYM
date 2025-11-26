package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Entrenador;
import com.GYM.proyecto_software.repositorio.EntrenadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
@CrossOrigin(origins = "*")
public class EntrenadorControlador {

    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;

    @GetMapping
    public List<Entrenador> getAllEntrenadores() {
        return entrenadorRepositorio.findAll();
    }

    @PostMapping
    public Entrenador createEntrenador(@RequestBody Entrenador entrenador) {
        return entrenadorRepositorio.save(entrenador);
    }
}