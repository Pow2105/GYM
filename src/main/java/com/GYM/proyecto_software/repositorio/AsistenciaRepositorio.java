package com.GYM.proyecto_software.repositorio;

import com.GYM.proyecto_software.modelo.Asistencia;
import com.GYM.proyecto_software.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsistenciaRepositorio extends JpaRepository<Asistencia, Long> {

    // Buscar la última asistencia registrada de un cliente
    // Ordenamos por fecha de entrada descendente
    Optional<Asistencia> findTopByClienteOrderByEntradaDesc(Cliente cliente);
}