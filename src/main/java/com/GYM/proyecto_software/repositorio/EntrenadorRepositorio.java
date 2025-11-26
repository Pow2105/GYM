package com.GYM.proyecto_software.repositorio;
import com.GYM.proyecto_software.modelo.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepositorio extends JpaRepository<Entrenador, Long> {}