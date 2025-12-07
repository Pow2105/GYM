package com.GYM.proyecto_software;

import jakarta.annotation.PostConstruct; // Importar esto
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone; // Importar esto

@SpringBootApplication
public class ProyectoSoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoSoftwareApplication.class, args);
    }


    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
        System.out.println("✅ Zona horaria configurada a: America/Bogota (" + new java.util.Date() + ")");
    }
}