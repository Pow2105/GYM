package com.GYM.proyecto_software;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // <--- NUEVO IMPORT
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ProyectoSoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoSoftwareApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
        System.out.println("✅ Zona horaria: America/Bogota");
    }
}