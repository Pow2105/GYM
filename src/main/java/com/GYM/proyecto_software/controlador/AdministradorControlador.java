package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Administrador;
import com.GYM.proyecto_software.repositorio.AdministradorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "*")
public class AdministradorControlador {

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección para encriptar

    @GetMapping
    public List<Administrador> getAllAdministradores() {
        return administradorRepositorio.findAll();
    }

    @PostMapping("/registro")
    public Administrador crearAdministrador(@RequestBody Administrador admin) {
        // 1. Encriptar contraseña
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }

        // 2. Asignar Rol
        admin.setRol("ADMIN");

        return administradorRepositorio.save(admin);
    }
}