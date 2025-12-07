package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Entrenador;
import com.GYM.proyecto_software.repositorio.EntrenadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
@CrossOrigin(origins = "*")
public class EntrenadorControlador {

    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección para encriptar

    @GetMapping
    public List<Entrenador> getAllEntrenadores() {
        return entrenadorRepositorio.findAll();
    }

    @PostMapping
    public Entrenador createEntrenador(@RequestBody Entrenador entrenador) {
        // 1. Encriptar contraseña
        if (entrenador.getPassword() != null && !entrenador.getPassword().isEmpty()) {
            entrenador.setPassword(passwordEncoder.encode(entrenador.getPassword()));
        }

        // 2. Asignar Rol
        entrenador.setRol("ENTRENADOR");

        return entrenadorRepositorio.save(entrenador);
    }
}