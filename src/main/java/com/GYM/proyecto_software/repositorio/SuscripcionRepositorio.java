package com.GYM.proyecto_software.repositorio;

import com.GYM.proyecto_software.modelo.Suscripcion;
import com.GYM.proyecto_software.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SuscripcionRepositorio extends JpaRepository<Suscripcion, Long> {
    Optional<Suscripcion> findByClienteAndEstado(Cliente cliente, String estado);

    List<Suscripcion> findByCliente_IdCliente(Long idCliente);
}