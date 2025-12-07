package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Entrenador;
import com.GYM.proyecto_software.repositorio.EntrenadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        return entrenadorRepositorio.findByEmail(email)
                .filter(e -> e.getPassword() != null && e.getPassword().equals(password))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}