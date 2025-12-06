package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Administrador;
import com.GYM.proyecto_software.repositorio.AdministradorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "*")
public class AdministradorControlador {

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    // Listar todos los administradores
    @GetMapping
    public List<Administrador> getAllAdministradores() {
        return administradorRepositorio.findAll();
    }

    // Registrar un nuevo administrador
    @PostMapping("/registro")
    public Administrador crearAdministrador(@RequestBody Administrador admin) {
        return administradorRepositorio.save(admin);
    }

    // Login simple (Devuelve el admin si las credenciales coinciden, o 401 si falla)
    // NOTA: En producción, esto debería usar Spring Security y encriptación de contraseñas.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        return administradorRepositorio.findByEmail(email)
                .filter(admin -> admin.getPassword().equals(password))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}