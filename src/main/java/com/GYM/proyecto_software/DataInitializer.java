package com.GYM.proyecto_software.config;

import com.GYM.proyecto_software.modelo.Administrador;
import com.GYM.proyecto_software.modelo.Entrenador;
import com.GYM.proyecto_software.repositorio.AdministradorRepositorio;
import com.GYM.proyecto_software.repositorio.EntrenadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdministradorRepositorio adminRepo;

    @Autowired
    private EntrenadorRepositorio entrenadorRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("---- INICIANDO CARGA DE DATOS DE PRUEBA ----");

        crearAdminSiNoExiste("Admin Principal", "admin123@gmail.com", "admin1029");

        crearEntrenadorSiNoExiste("Angel", "angelre@gmail.com", "angel0913", "Musculación", "Mañana");
        crearEntrenadorSiNoExiste("Roberto", "robertorrh@gmail.com", "robertmolly21", "Cardio/Fitness", "Tarde");
        crearEntrenadorSiNoExiste("Andres", "andresRRA@gmail.com", "ARRA2105", "Crossfit", "Noche");

        System.out.println("---- CARGA DE DATOS COMPLETADA ----");
    }

    private void crearAdminSiNoExiste(String nombre, String email, String password) {
        if (adminRepo.findByEmail(email).isEmpty()) {
            Administrador admin = new Administrador();
            admin.setNombre(nombre);
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password)); // ¡Encriptamos la contraseña!
            admin.setRol("ADMIN");
            adminRepo.save(admin);
            System.out.println("✅ Administrador creado: " + email);
        } else {
            System.out.println("ℹ️ El administrador " + email + " ya existe.");
        }
    }

    private void crearEntrenadorSiNoExiste(String nombre, String email, String password, String especialidad, String turno) {
        if (entrenadorRepo.findByEmail(email).isEmpty()) {
            Entrenador entrenador = new Entrenador();
            entrenador.setNombre(nombre);
            entrenador.setEmail(email);
            entrenador.setPassword(passwordEncoder.encode(password)); // ¡Encriptamos la contraseña!
            entrenador.setRol("ENTRENADOR");
            entrenador.setEspecialidad(especialidad);
            entrenador.setDisponibilidad(turno);
            entrenadorRepo.save(entrenador);
            System.out.println("✅ Entrenador creado: " + email);
        } else {
            System.out.println("ℹ️ El entrenador " + email + " ya existe.");
        }
    }
}