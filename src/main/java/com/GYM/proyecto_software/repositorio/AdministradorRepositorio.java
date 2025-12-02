package com.GYM.proyecto_software.repositorio;

import com.GYM.proyecto_software.modelo.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdministradorRepositorio extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);
}